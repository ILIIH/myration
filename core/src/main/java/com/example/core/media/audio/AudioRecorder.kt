package com.example.core.media.audio

import android.Manifest
import android.content.Context
import android.content.res.AssetManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.annotation.RequiresPermission
import java.io.*

class WavAudioRecorder(private val context: Context) {

    private var audioRecord: AudioRecord? = null
    private var isRecording = false
    private lateinit var recordingThread: Thread
    private var outputFilePath: String = ""

    private val sampleRate = 16000 // ✅ Updated
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    @RequiresPermission(Manifest.permission.RECORD_AUDIO)
    fun startRecording() {
        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        audioRecord?.startRecording()
        isRecording = true

        outputFilePath = "${context.externalCacheDir?.absolutePath}/recorded_audio.wav"
        recordingThread = Thread { writePcmToWav(outputFilePath) }
        recordingThread.start()
    }

    fun stopRecording(): String {
        isRecording = false
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null

        if (::recordingThread.isInitialized) {
            try {
                recordingThread.join()
            } catch (e: InterruptedException) {
                Log.e("WavRecorder", "Thread join failed", e)
            }
        }

        return outputFilePath
    }

    fun isRecording(): Boolean = isRecording

    private fun writePcmToWav(filePath: String) {
        val pcmBuffer = ByteArray(bufferSize)
        val wavFile = File(filePath)

        FileOutputStream(wavFile).use { outputStream ->
            writeWavHeader(outputStream, sampleRate, 1, 16)

            var totalAudioLen = 0
            while (isRecording) {
                val read = audioRecord?.read(pcmBuffer, 0, pcmBuffer.size) ?: 0
                if (read > 0) {
                    outputStream.write(pcmBuffer, 0, read)
                    totalAudioLen += read
                }
            }

            updateWavHeader(wavFile, totalAudioLen.toLong(), sampleRate, 1, 16)
        }
    }

    private fun writeWavHeader(out: OutputStream, sampleRate: Int, channels: Int, bitsPerSample: Int) {
        val byteRate = sampleRate * channels * bitsPerSample / 8
        val header = ByteArray(44)

        // RIFF/WAVE header
        header[0] = 'R'.code.toByte(); header[1] = 'I'.code.toByte(); header[2] = 'F'.code.toByte(); header[3] = 'F'.code.toByte()
        // ChunkSize placeholder (file size - 8 bytes), updated later
        header[8] = 'W'.code.toByte(); header[9] = 'A'.code.toByte(); header[10] = 'V'.code.toByte(); header[11] = 'E'.code.toByte()
        // fmt subchunk
        header[12] = 'f'.code.toByte(); header[13] = 'm'.code.toByte(); header[14] = 't'.code.toByte(); header[15] = ' '.code.toByte()
        header[16] = 16; header[17] = 0; header[18] = 0; header[19] = 0 // fmt chunk size = 16
        header[20] = 1; header[21] = 0 // Audio format = 1 (PCM)
        header[22] = channels.toByte(); header[23] = 0
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = ((sampleRate shr 8) and 0xff).toByte()
        header[26] = ((sampleRate shr 16) and 0xff).toByte()
        header[27] = ((sampleRate shr 24) and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = ((byteRate shr 8) and 0xff).toByte()
        header[30] = ((byteRate shr 16) and 0xff).toByte()
        header[31] = ((byteRate shr 24) and 0xff).toByte()
        header[32] = (channels * bitsPerSample / 8).toByte(); header[33] = 0 // block align
        header[34] = bitsPerSample.toByte(); header[35] = 0
        // data subchunk
        header[36] = 'd'.code.toByte(); header[37] = 'a'.code.toByte(); header[38] = 't'.code.toByte(); header[39] = 'a'.code.toByte()
        // Subchunk2Size placeholder (data size), updated later

        out.write(header, 0, 44)
    }

    private fun updateWavHeader(wavFile: File, totalAudioLen: Long, sampleRate: Int, channels: Int, bitsPerSample: Int) {
        val totalDataLen = totalAudioLen + 36
        val byteRate = sampleRate * channels * bitsPerSample / 8

        val raf = RandomAccessFile(wavFile, "rw")
        raf.seek(4)
        raf.write(intToLittleEndian(totalDataLen.toInt()))
        raf.seek(40)
        raf.write(intToLittleEndian(totalAudioLen.toInt()))
        raf.close()
    }

    private fun intToLittleEndian(value: Int): ByteArray {
        return byteArrayOf(
            (value and 0xff).toByte(),
            ((value shr 8) and 0xff).toByte(),
            ((value shr 16) and 0xff).toByte(),
            ((value shr 24) and 0xff).toByte()
        )
    }
}
