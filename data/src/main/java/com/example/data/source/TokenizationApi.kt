package com.example.data.source
import com.example.domain.model.Tokenizer.TokenizeRequest
import com.example.domain.model.Tokenizer.TokenizeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface TokenizationApi {
    @POST("/tokenize")
    suspend fun tokenize(@Body request: TokenizeRequest): Response<TokenizeResponse>
}
