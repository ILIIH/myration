package com.example.domain.model.Tokenizer

data class TokenizeRequest(
    val text: List<String>,
    val boxes: List<List<Int>>
)