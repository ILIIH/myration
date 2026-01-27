package com.example.data.repository

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.model.FoodPlanIngredientEntity
import com.example.data.model.ProductEntity
import com.example.data.model.llm.LlmRequest
import com.example.data.model.llm.Message
import com.example.data.model.llm.RawNutritionResponse
import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.model.toData
import com.example.data.source.FoodPlanDataSource
import com.example.data.source.FoodPlanIngredientDataSource
import com.example.data.source.LlmApi
import com.example.data.source.ProductLocalDataSource
import com.example.domain.model.FoodPlan
import com.example.domain.model.FoodPlanIngredient
import com.example.domain.repository.FoodPlanRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FoodPlanRepositoryImp @Inject constructor(
    private val foodPlanDao: FoodPlanDataSource,
    private val api: LlmApi,
    private val productDao: ProductLocalDataSource,
    private val foodPlanIngredientsDao: FoodPlanIngredientDataSource
) : FoodPlanRepository {
    override suspend fun addFoodPlan(foodPlan: List<FoodPlan>) {
        withContext(Dispatchers.IO) {
            foodPlanDao.deleteFoodPlanByDate(foodPlan.firstOrNull()?.date ?: "")
            foodPlanDao.addFoodPlan(foodPlan.map { it.toData() })

            for (plan in foodPlan) {
                for (ingredient in plan.ingredients) {
                    foodPlanIngredientsDao.addFoodPlanIngredient(
                        FoodPlanIngredientEntity(
                            amountGrams = ingredient.amountGrams,
                            name = ingredient.name,
                            amountSource = ingredient.amountSource,
                            calories = ingredient.calories.toString(),
                            foodPlanId = plan.id,
                            active = true,
                            productId = ingredient.productId
                        )
                    )
                }
            }
        }
    }

    override suspend fun getFoodPlans(date: String): List<FoodPlan> {
        return foodPlanDao.getAllFoodPlanByDate(date).map { it ->
            val ingredients = foodPlanIngredientsDao.getFoodPlanIngredientEntity(it.id)
            it.toDomain(ingredients.map { ing -> ing.toData() })
        }
    }

    override suspend fun deactivateFoodPlanAndRelatedIng(foodPlan: FoodPlan) {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm")

        for (ingredient in foodPlan.ingredients) {
            productDao.changeProductAmount(ingredient.productId, ingredient.amountSource)
        }
        foodPlanDao.updateFoodPlan(
            foodPlan.copy(
                completed = true,
                completionTime = current.format(formatter)
            ).toData()
        )
        foodPlanIngredientsDao.deactivateFoodPlanIngredient(foodPlan.id)
    }

    override suspend fun deleteFoodPlan(date: String) {
        foodPlanDao.deleteFoodPlanByDate(date)
    }

    override suspend fun generateFoodPlan(
        caloriesPerDay: Int,
        numberOfMeals: Int,
        foodPref: String,
        date: String
    ): List<FoodPlan> = withContext(Dispatchers.IO) {
        val productsList = productDao.getAllProduct()

        val prompt = generatePrompt(caloriesPerDay, numberOfMeals, foodPref, productsList)
        val apiKey = BuildConfig.LLM_API_KEY

        val request = LlmRequest(
            messages = listOf(
                Message("system", "You are a professional nutritionist. Output ONLY valid JSON."),
                Message("user", prompt)
            )
        )

        try {
            val response = api.getContent("Bearer $apiKey", request)
            if (response.isSuccessful) {
                val rawJsonString = response.body()?.choices?.firstOrNull()?.message?.content

                if (rawJsonString != null) {
                    val gson = Gson()
                    val rawData = gson.fromJson(rawJsonString, RawNutritionResponse::class.java)

                    var lastFoodPlanId = foodPlanDao.getLastIdOrZero()

                    rawData.meals.flatMap { slot ->
                        slot.options.map { option ->
                            lastFoodPlanId++
                            val ingredientsList = mutableListOf<FoodPlanIngredient>()
                            for (ingredient in option.ingredients) {
                                val productId = productDao.getProductIdByName(ingredient.name)

                                ingredientsList.add(
                                    FoodPlanIngredient(
                                        name = ingredient.name,
                                        amountGrams = ingredient.amountGrams,
                                        amountSource = ingredient.amountSource,
                                        calories = ingredient.calories,
                                        foodPlanId = lastFoodPlanId,
                                        active = true,
                                        productId = productId
                                    )
                                )
                            }

                            FoodPlan(
                                id = lastFoodPlanId,
                                mealName = option.optionName,
                                mealCalorie = option.totalCalories,
                                mealNumber = slot.mealNumber,
                                completed = false,
                                completionTime = "",
                                date = date,
                                ingredients = ingredientsList
                            )
                        }
                    }.sortedBy { it.mealNumber }
                } else {
                    emptyList()
                }
            } else {
                println("Error: ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Log.i("my_ration_log", e.message ?: "")
            emptyList()
        }
    }
    private fun getProductListStr(productsList: List<ProductEntity>): String {
        val strBuilder = StringBuilder()
        strBuilder.append('[')
        for (product in productsList) {
            strBuilder.append(" ${product.name} ${product.weight}${product.measurementMetric} ,")
        }
        strBuilder.append(']')
        return strBuilder.toString()
    }

    private fun generatePrompt(calories: Int, numberOfMeals: Int, foodPref: String, productsList: List<ProductEntity>): String {
        val products = getProductListStr(productsList)

        return """
# ROLE
Professional Nutritionist API. You always return strictly valid JSON.

# INPUT DATA
- Target Daily Calories: $calories kcal
- Meal Slots to Fill: $numberOfMeals
- Preferred Diet: $foodPref
- Available Ingredients (Database): $products

# RULES
1. **Strict Isolation**: Each "option" must be a standalone recipe. NEVER merge ingredients from Option A into Option B. The ingredients list for an option must ONLY contain what is needed for that specific dish.
2. **Multiple Options**: For EACH `mealNumber`, provide 1 to 10 distinct, unique recipe options.
3. **Calorie Math**: 
    - Each meal slot should target ~$(${calories.toDouble() / numberOfMeals.toDouble()}) kcal.
    - `totalCalories` in an option MUST be the sum of its ingredients' calories.
    - `amountSource` MUST be a calculated positive number (Float) required to reach the target calories. NEVER return 0.0.
4. **Preference Flexibility**: Treat "Preferred Diet" ($foodPref) as a priority. If calorie goals cannot be met with preferred items, use other Available Ingredients. Never return an empty list.
5. **Field: optionName**: Short, descriptive dish name. No "Option 1" prefixes or slot names (e.g., "Avocado Toast", not "Breakfast Option 1").
6. **Field: ingredients**: 
    - "name" must match the "Available Ingredients" input EXACTLY.
    - Only include ingredients used in that specific option.
7. **Field: amountSource**: This is the quantity of the ingredient in its original unit (from the input list). It must be a calculated value > 0.

# MEAL MAPPING
1: Breakfast, 2: Lunch, 3: Dinner, 4: Second Breakfast, 5: Brunch, 6: Supper.

# OUTPUT FORMAT
Return ONLY valid JSON. Follow this structure exactly:
{
  "totalDailyTarget": $calories,
  "meals": [
    {
      "mealNumber": 1,
      "slotName": "Breakfast",
      "options": [
        {
          "optionName": "Specific Dish Name",
          "totalCalories": 0.0,
          "ingredients": [
            { 
              "name": "Exact Name From List", 
              "amountGrams": 0.0,  
              "amountSource": 0.0, 
              "calories": 0.0 
            }
          ]
        }
      ]
    }
  ]
}
        """.trimIndent()
    }
}
