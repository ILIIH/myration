package com.example.data.source

import com.example.data.model.NutritionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NutritionApi {
    @GET("v1/nutrition")
    suspend fun getNutrition(@Header("X-Api-Key") apiKey: String, @Query("query") query: String): NutritionResponse
}

