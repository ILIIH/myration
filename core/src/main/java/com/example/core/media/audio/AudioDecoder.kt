package com.example.core.media.audio

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

const val VOICE_RECOGNITION_MODEL_PATH = "models/voice_to_text_model"
const val LOG_TAG = "audio_recognition_logs"

class AudioDecoder(private val context: Context) {
    private var canTranscribe = false
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val modelsPath = File(context.filesDir, "models")
    private val samplesPath = File(context.filesDir, "samples")
    private var whisperContext: com.whispercpp.whisper.WhisperContext? = null
    private var recordedFile: File? = null

    init {
        scope.launch {
            loadModel()
        }
    }
    private suspend fun loadModel() {
        try {
            copyAssets()
            loadBaseModel()
            canTranscribe = true
        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }
    }
    private suspend fun copyAssets() = withContext(Dispatchers.IO) {
        modelsPath.mkdirs()
        samplesPath.mkdirs()
        context.copyData("samples", samplesPath)
    }

    private suspend fun loadBaseModel() = withContext(Dispatchers.IO) {
        val models = context.assets.list("models/")
        if (models != null) {
            whisperContext = com.whispercpp.whisper.WhisperContext.createContextFromAsset(context.assets, "models/" + models[0])
        }
    }
    private suspend fun runBenchmark(nthreads: Int) {
        if (!canTranscribe) {
            return
        }

        canTranscribe = false

        // whisperContext?.benchMemory(nthreads)?.let{ printMessage(it) }
        // whisperContext?.benchGgmlMulMat(nthreads)?.let{ printMessage(it) }

        canTranscribe = true
    }

    suspend fun transcribeAudio(fileName: String) {
        if (!canTranscribe) {
            return
        }

        canTranscribe = false

        try {
            val file = File(context.externalCacheDir, fileName)
            val start = System.currentTimeMillis()
            val data = readAudio(file)
            val text = whisperContext?.transcribeData(data)
            val elapsed = System.currentTimeMillis() - start
            Log.w(LOG_TAG, "Done ($elapsed ms): \n$text\n")
        } catch (e: Exception) {
            Log.w(LOG_TAG, e)
        }

        canTranscribe = true
    }


    private suspend fun readAudio(file: File): FloatArray = withContext(Dispatchers.IO) {
        return@withContext decodeMPEG_4(file)
    }
    private suspend fun getTempFileForRecording() = withContext(Dispatchers.IO) {
        File.createTempFile("recording", "wav")
    }

    fun onCleared() {
        scope.launch {
            whisperContext?.release()
            whisperContext = null
        }
        scope.cancel()
    }
}

private suspend fun Context.copyData(
    assetDirName: String,
    destDir: File
) = withContext(Dispatchers.IO) {
    assets.list(assetDirName)?.forEach { name ->
        val assetPath = "$assetDirName/$name"
        Log.v(LOG_TAG, "Processing $assetPath...")
        val destination = File(destDir, name)
        Log.v(LOG_TAG, "Copying $assetPath to $destination...")
        assets.open(assetPath).use { input ->
            destination.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        Log.v(LOG_TAG, "Copied $assetPath to $destination")
    }
}