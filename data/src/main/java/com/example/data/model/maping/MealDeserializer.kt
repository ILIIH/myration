package com.example.data.model.maping

import com.example.data.model.RecipeAPIEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import java.lang.reflect.Type

class MealDeserializer : JsonDeserializer<RecipeAPIEntity> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RecipeAPIEntity {
        val jsonObject = json.asJsonObject
        val name = jsonObject.get("strMeal")?.takeIf { it !is JsonNull }?.asString ?: ""
        val id = jsonObject.get("idMeal")?.takeIf { it !is JsonNull }?.asInt ?: 0
        val alternateName = jsonObject.get("strMealAlternate")?.takeIf { it !is JsonNull }?.asString
        val category = jsonObject.get("strCategory")?.takeIf { it !is JsonNull }?.asString
        val area = jsonObject.get("strArea")?.takeIf { it !is JsonNull }?.asString
        val instructions = jsonObject.get("strInstructions")?.takeIf { it !is JsonNull }?.asString
        val thumbnail = jsonObject.get("strMealThumb")?.takeIf { it !is JsonNull }?.asString
        val tags = jsonObject.get("strTags")?.takeIf { it !is JsonNull }?.asString
        val youtube = jsonObject.get("strYoutube")?.takeIf { it !is JsonNull }?.asString
        val source = jsonObject.get("strSource")?.takeIf { it !is JsonNull }?.asString
        val imageSource = jsonObject.get("strImageSource")?.takeIf { it !is JsonNull }?.asString
        val creativeCommonsConfirmed = jsonObject.get("strCreativeCommonsConfirmed")?.takeIf { it !is JsonNull }?.asString
        val dateModified = jsonObject.get("dateModified")?.takeIf { it !is JsonNull }?.asString

        // Extract ingredients (ignoring empty and null values)
        val ingredients = (1..20).mapNotNull { index ->
            jsonObject.get("strIngredient$index")?.takeIf { it !is JsonNull }?.asString?.takeIf { it.isNotBlank() }
        }

        // Extract measures (matching ingredient indices)
        val measures = (1..20).mapNotNull { index ->
            jsonObject.get("strMeasure$index")?.takeIf { it !is JsonNull }?.asString?.takeIf { it.isNotBlank() }
        }

        return RecipeAPIEntity(id, name, alternateName, category, area, instructions, thumbnail, tags, youtube, source, imageSource, creativeCommonsConfirmed, dateModified, ingredients, measures)
    }
}
