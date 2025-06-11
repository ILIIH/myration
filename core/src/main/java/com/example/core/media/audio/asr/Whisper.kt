package com.example.core.media.audio.asr

import android.content.Context
import android.util.Log
import com.example.core.media.audio.engine.WhisperEngine
import com.example.core.media.audio.engine.WhisperEngineNative
import java.io.File
import java.io.IOException
import java.util.LinkedList
import java.util.Queue
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.Volatile


class Whisper(context: Context) {
    interface WhisperListener {
        fun onUpdateReceived(message: String?)
        fun onResultReceived(result: String?)
    }

    enum class Action {
        TRANSLATE, TRANSCRIBE
    }

    private val mInProgress = AtomicBoolean(false)
    private val audioBufferQueue: Queue<FloatArray> = LinkedList()

    //        this.mWhisperEngine = new WhisperEngineJava(context);
    private val mWhisperEngine: WhisperEngine = WhisperEngineNative(context)
    private var mAction: Action? = null
    private var mWavFilePath: String? = null
    private var mUpdateListener: WhisperListener? = null

    private val taskLock: Lock = ReentrantLock()
    private val hasTask: Condition = taskLock.newCondition()

    @Volatile
    private var taskAvailable = false

    init {
        // Start thread for file transcription for file transcription
        // Start thread for buffer transcription for live mic feed transcription
        val threadTranscbBuffer = Thread { this.transcribeBufferLoop() }
        threadTranscbBuffer.start()
    }

    fun setListener(listener: WhisperListener?) {
        this.mUpdateListener = listener
    }

    fun loadModel(modelPath: File, vocabPath: File, isMultilingual: Boolean) {
        loadModel(modelPath.absolutePath, vocabPath.absolutePath, isMultilingual)
    }

    fun loadModel(modelPath: String?, vocabPath: String?, isMultilingual: Boolean) {
        try {
            mWhisperEngine.initialize(modelPath, vocabPath, isMultilingual)
        } catch (e: IOException) {
            Log.e(TAG, "Error initializing model...", e)
            sendUpdate("Model initialization failed")
        }
    }

    fun unloadModel() {
        mWhisperEngine.deinitialize()
    }

    fun setAction(action: Action?) {
        this.mAction = action
    }

    fun setFilePath(wavFile: String?) {
        this.mWavFilePath = wavFile
    }

    fun start() {
        if (!mInProgress.compareAndSet(false, true)) {
            Log.d(TAG, "Execution is already in progress...")
            return
        }
        taskLock.lock()
        try {
            taskAvailable = true
            hasTask.signal()
        } finally {
            taskLock.unlock()
        }
    }

    fun stop() {
        mInProgress.set(false)
    }

    val isInProgress: Boolean
        get() = mInProgress.get()


    private fun sendUpdate(message: String) {
        if (mUpdateListener != null) {
            mUpdateListener!!.onUpdateReceived(message)
        }
    }

    private fun sendResult(message: String?) {
        if (mUpdateListener != null) {
            mUpdateListener!!.onResultReceived(message)
        }
    }

    /**//////////////////// Live MIC feed transcription calls ///////////////////////////////// */
    private fun transcribeBufferLoop() {
        while (!Thread.currentThread().isInterrupted) {
            val samples = readBuffer()
            if (samples != null) {
                synchronized(mWhisperEngine) {
                    val result = mWhisperEngine.transcribeBuffer(samples)
                    sendResult(result)
                }
            }
        }
    }

    fun writeBuffer(samples: FloatArray) {
        synchronized(audioBufferQueue) {
            audioBufferQueue.add(samples)
            (audioBufferQueue as Object).notify()
        }
    }

    private fun readBuffer(): FloatArray? {
        synchronized(audioBufferQueue) {
            while (audioBufferQueue.isEmpty()) {
                try {
                    (audioBufferQueue as Object).wait()
                } catch (e: InterruptedException) {
                    Thread.currentThread().interrupt()
                    return null
                }
            }
            return audioBufferQueue.poll()
        }
    }

    companion object {
        private const val TAG = "Whisper"
        const val MSG_PROCESSING: String = "Processing..."
        const val MSG_PROCESSING_DONE: String = "Processing done...!"
        const val MSG_FILE_NOT_FOUND: String = "Input file doesn't exist..!"

        val ACTION_TRANSCRIBE: Action = Action.TRANSCRIBE
        val ACTION_TRANSLATE: Action = Action.TRANSLATE
    }
}
