package com.example.core.media.audio

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.IOException

class AudioRecorder(private val context: Context) {

    private var recorder: MediaRecorder? = null
    private val filePath: String = "${context.externalCacheDir?.absolutePath}/$AUDIO_FILE_NAME"
    private var isRecording = false

    fun startRecording() {
        stopRecording() // Just in case

        recorder = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

        try {
            recorder?.apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
                setOutputFile(filePath)
                prepare()
                start()
                isRecording = true
            }
        } catch (e: IOException) {
            Log.e("AudioRecorder", "prepare() failed", e)
        } catch (e: IllegalStateException) {
            Log.e("AudioRecorder", "start() failed", e)
        }
    }

    fun stopRecording(){
        try {
            if (isRecording) {
                recorder?.apply {
                    stop()
                    reset()
                    release()
                }
                isRecording = false
            }
        } catch (e: IllegalStateException) {
            Log.e("AudioRecorder", "stop() failed", e)
        } finally {
            recorder = null
        }
    }

    companion object {
        const val AUDIO_FILE_NAME = "recorded_audio.mp4"
    }

    fun isRecording(): Boolean = isRecording
}
