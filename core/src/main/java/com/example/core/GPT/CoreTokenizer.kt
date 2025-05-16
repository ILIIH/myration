package com.example.core.GPT

import org.json.JSONObject

class CoreTokenizer(
    private val vocab: Map<String, Int>,
    private val unkToken: String = "[UNK]",
    private val clsToken: String = "[CLS]",
    private val sepToken: String = "[SEP]",
    private val padToken: String = "[PAD]",
    private val maxInputCharsPerWord: Int = 100
) {

    data class TokenizedResult(
        val inputIds: List<Int>,
        val tokenToWordMap: List<Int>
    )

    fun tokenize(words: List<String>): TokenizedResult {
        val inputIds = mutableListOf<Int>()
        val tokenToWordMap = mutableListOf<Int>()

        // Add [CLS] token
        inputIds.add(vocab[clsToken] ?: error("Missing CLS token in vocab"))
        tokenToWordMap.add(-1)

        for ((wordIdx, word) in words.withIndex()) {
            val lowerWord = word.lowercase()

            if (lowerWord.length > maxInputCharsPerWord) {
                inputIds.add(vocab[unkToken] ?: error("Missing UNK token in vocab"))
                tokenToWordMap.add(wordIdx)
                continue
            }

            var start = 0
            val subTokens = mutableListOf<String>()
            while (start < lowerWord.length) {
                var end = lowerWord.length
                var curSubstr: String? = null

                while (start < end) {
                    var substr = lowerWord.substring(start, end)
                    if (start > 0) substr = "##$substr"
                    if (vocab.containsKey(substr)) {
                        curSubstr = substr
                        break
                    }
                    end -= 1
                }

                if (curSubstr == null) {
                    subTokens.clear()
                    break
                }

                subTokens.add(curSubstr)
                start = end
            }

            if (subTokens.isEmpty()) {
                inputIds.add(vocab[unkToken] ?: error("Missing UNK token in vocab"))
                tokenToWordMap.add(wordIdx)
            } else {
                inputIds.addAll(subTokens.map { vocab[it]!! })
                repeat(subTokens.size) { tokenToWordMap.add(wordIdx) }
            }
        }

        // Add [SEP] token
        inputIds.add(vocab[sepToken] ?: error("Missing SEP token in vocab"))
        tokenToWordMap.add(-1)

        return TokenizedResult(inputIds, tokenToWordMap)
    }

    companion object {
        fun fromJson(jsonString: String): CoreTokenizer {
            val jsonObject = JSONObject(jsonString)
            val vocabJson = jsonObject.getJSONObject("model").getJSONObject("vocab")

            val tokenToId = mutableMapOf<String, Int>()
            vocabJson.keys().forEach { key ->
                tokenToId[key] = vocabJson.getInt(key)
            }
            return CoreTokenizer(tokenToId)
        }
    }
}
