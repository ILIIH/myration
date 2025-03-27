package com.example.core.util

import android.content.Context
import android.media.MediaRecorder
import android.os.Build

class AudioRecorder(val context: Context) {

    val filePath = "${context.externalCacheDir?.absolutePath}/recorded_audio.3gp"

    var recorder = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }

    fun setupRecorder(filePath: String) {
        recorder.apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            setOutputFile(filePath)
            prepare()
        }
    }

    fun startRecording() {

        recorder = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
            MediaRecorder(context)
        } else {
            MediaRecorder()
        }
        setupRecorder(filePath)
    }

    fun stopRecording() {
        recorder.apply {
            stop()
            reset()
            release()
        }
    }
}