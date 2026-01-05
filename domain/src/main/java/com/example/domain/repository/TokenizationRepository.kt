package com.example.domain.repository

import com.example.domain.model.Tokenizer.TokenizeResponse

interface TokenizationRepository {
    suspend fun tokenize(text: List<String>, boxes: List<List<Int>>): TokenizeResponse
}
