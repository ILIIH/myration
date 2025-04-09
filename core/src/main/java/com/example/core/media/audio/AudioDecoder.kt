package com.example.core.media.audio

import android.content.Context
import android.util.Log
import com.example.core.media.audio.whisper.WhisperContext
import java.io.File

const val VOICE_RECOGNITION_MODEL_PATH = "models/voice_to_text_model"
const val LOG_TAG = "audio_recognition_logs"

class AudioDecoder(private val context: Context) {

    private var whisperContext: WhisperContext? = null
    private var canTranscribe = false

    init{
        loadModel()
    }
    private fun loadModel(){
        whisperContext = WhisperContext.createContextFromAsset(context.assets, VOICE_RECOGNITION_MODEL_PATH)
    }

    suspend fun transcribeAudio(fileName: String) {
        val file = File(context.externalCacheDir, fileName)

        if (!canTranscribe) {
            return
        }

        canTranscribe = false

        try {
            val data = decodeMPEG_4(file)
            val start = System.currentTimeMillis()
            val text = whisperContext?.transcribeData(data)
            Log.i(LOG_TAG, text?:"")

            val elapsed = System.currentTimeMillis() - start
        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }

        canTranscribe = true
    }
}