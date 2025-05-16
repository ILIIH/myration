package com.example.domain.model.Tokenizer

data class TokenizeResponse(
    val input_ids: LongArray,
    val attention_mask: LongArray,
    val token_type_ids: LongArray,
    val bbox: List<LongArray>
) {
    companion object {
        fun getEmpty(): TokenizeResponse {
            return TokenizeResponse(
                input_ids = longArrayOf(),
                attention_mask = longArrayOf(),
                token_type_ids = longArrayOf(),
                bbox = emptyList()
            )
        }
    }
}
