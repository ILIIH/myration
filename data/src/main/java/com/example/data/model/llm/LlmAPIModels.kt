package com.example.data.model.llm

data class LlmRequest(
    val messages: List<Message>,
    val model: String = "llama-3.3-70b-versatile",
    val response_format: ResponseFormat = ResponseFormat("json_object")
)

data class Message(
    val role: String,
    val content: String
)

data class ResponseFormat(
    val type: String
)

// Response Models
data class LlmResponse(
    val choices: List<Choice>
)

data class Choice(
    val message: Message
)

data class RawNutritionResponse(
    val meals: List<RawMealSlot>
)

data class RawMealSlot(
    val mealNumber: Int,
    val slotName: String,
    val options: List<RawMealOption>
)

data class RawMealOption(
    val optionName: String,
    val totalCalories: Double,
    val ingredients: List<RawIngredient>
)

data class RawIngredient(
    val name: String,
    val amountGrams: Double,
    val amountSource: Float,
    val calories: Float
)
