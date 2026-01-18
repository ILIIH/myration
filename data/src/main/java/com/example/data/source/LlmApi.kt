package com.example.data.source
import com.example.data.model.llm.LlmRequest
import com.example.data.model.llm.LlmResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LlmApi {
    @POST("openai/v1/chat/completions")
    suspend fun getContent(
        @Header("Authorization") authHeader: String,
        @Body request: LlmRequest
    ): Response<LlmResponse>
}
