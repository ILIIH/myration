package com.example.core.util

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.IOException

class AudioRecorder(private val context: Context) {

    private var recorder: MediaRecorder? = null
    val filePath: String = "${context.externalCacheDir?.absolutePath}/recorded_audio.3gp"
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
                setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
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

    fun stopRecording() {
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

    fun isRecording(): Boolean = isRecording
}