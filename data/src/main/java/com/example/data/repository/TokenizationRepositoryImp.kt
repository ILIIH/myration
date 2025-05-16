package com.example.data.repository

import android.util.Log
import com.example.data.source.TokenizationApi
import com.example.domain.model.Tokenizer.TokenizeRequest
import com.example.domain.model.Tokenizer.TokenizeResponse
import com.example.domain.repository.TokenizationRepository
import javax.inject.Inject

class TokenizationRepositoryImp  @Inject constructor(
    private val tokenizationApi: TokenizationApi
): TokenizationRepository {

    override suspend fun tokenize(text: List<String>, boxes: List<List<Int>>): TokenizeResponse {
        val request = TokenizeRequest(text = text, boxes = boxes)

        return try {
            val response = tokenizationApi.tokenize(request)
            response.body() ?: TokenizeResponse.getEmpty()
        } catch (e: Exception) {
            Log.i("image_scanning","Tokenization failed: ${e.message}")
            TokenizeResponse.getEmpty()
        }
    }

}