package com.example.data.repository

import com.example.data.BuildConfig
import com.example.data.model.ProductEntity
import com.example.data.model.llm.LlmRequest
import com.example.data.model.llm.Message
import com.example.data.model.llm.RawNutritionResponse
import com.example.data.model.maping.toData
import com.example.data.model.maping.toDomain
import com.example.data.source.FoodPlanDataSource
import com.example.data.source.LlmApi
import com.example.data.source.ProductLocalDataSource
import com.example.domain.model.FoodPlan
import com.example.domain.repository.FoodPlanRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class FoodPlanRepositoryImp @Inject constructor(
    private val foodPlanDao: FoodPlanDataSource,
    private val api: LlmApi,
    private val productDao: ProductLocalDataSource
) : FoodPlanRepository {
    override suspend fun addFoodPlan(foodPlan: List<FoodPlan>) {
        foodPlanDao.deleteFoodPlanByDate(foodPlan.firstOrNull()?.date ?: "")
        foodPlanDao.addFoodPlan(foodPlan.map { it.toData() })
    }

    override suspend fun getFoodPlan(date: String): List<FoodPlan> {
        return foodPlanDao.getAllFoodPlanByDate().map { it.toDomain() }
    }

    override suspend fun deleteFoodPlan(date: String) {
        foodPlanDao.deleteFoodPlanByDate(date)
    }

    override suspend fun generateFoodPlan(
        caloriesPerDay: Int,
        numberOfMeals: Int,
        foodPref: String
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

                    val tomorrow = LocalDate.now().plusDays(1)
                    val date = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

                    rawData.meals.flatMap { slot ->
                        slot.options.map { option ->
                            FoodPlan(
                                mealName = option.optionName,
                                mealCalorie = option.totalCalories.toFloat(),
                                mealNumber = slot.mealNumber,
                                completed = false,
                                completionTime = "",
                                date = date,
                                amountGramsIng = option.ingredients.joinToString(", ") {
                                    "${it.name} (${it.amountGrams.toInt()}g)"
                                }
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
    Professional Nutritionist API. You always return JSON.

    # INPUT DATA
    - Target Daily Calories: $calories kcal
    - Meal Slots to Fill: $numberOfMeals (mapped below)
    - Preferred Diet: $foodPref
    - Available Ingredients: $products

    # RULES
    1. **Variety & Diversity**: Do not repeat the same meal composition. Use as many different ingredients from the list as possible to create a diversified ration.
    2. **Multiple Options**: For EACH `mealNumber`, provide from 1 to 10 distinct "options" (different recipes/combinations) using the available ingredients, provided the ingredient list is large enough.
    3. **Preference Flexibility**: Treat "Preferred Diet" ($foodPref) as a strong suggestion. If you cannot meet the calorie goal ($calories) using only preferred items, you MUST use other available ingredients. NEVER return an empty list.
    4. **Math**: Each meal option should represent roughly its portion of the daily calories (e.g., if 4 meals total, each option should be ~25% of the $calories kcal).

    # MEAL MAPPING
    1: Breakfast, 2: Lunch, 3: Dinner, 4: Second Breakfast, 5: Brunch, 6: Supper.

    # OUTPUT FORMAT
    Return ONLY valid JSON.
    {
      "totalDailyTarget": $calories,
      "meals": [
        {
          "mealNumber": 1,
          "slotName": "Breakfast",
          "options": [
            {
              "optionName": "Option 1 Name",
              "totalCalories": 0.0,
              "ingredients": [
                { "name": "Item", "amountGrams": 0.0, "calories": 0.0 }
              ]
            }
          ]
        }
      ]
    }
        """.trimIndent()
    }
}
