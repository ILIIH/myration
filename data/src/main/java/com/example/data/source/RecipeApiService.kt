package com.example.data.source

import com.example.data.model.MealsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("/api/json/v1/1/search.php")
    suspend fun getRecipeStartedWith(@Query("f") mealStartChar: Char): MealsResponse
}
