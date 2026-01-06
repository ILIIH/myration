package com.example.core.media.audio

import android.media.MediaCodec
import android.media.MediaExtractor
import android.media.MediaFormat
import java.io.File
import java.nio.ByteOrder

fun decodeMPEG_4(file: File): FloatArray {
    val extractor = MediaExtractor()
    extractor.setDataSource(file.absolutePath)

    // Select the first audio track
    var trackIndex = -1
    for (i in 0 until extractor.trackCount) {
        val format = extractor.getTrackFormat(i)
        val mime = format.getString(MediaFormat.KEY_MIME) ?: ""
        if (mime.startsWith("audio/")) {
            trackIndex = i
            break
        }
    }

    if (trackIndex == -1) throw IllegalArgumentException("No audio track found")

    extractor.selectTrack(trackIndex)
    val format = extractor.getTrackFormat(trackIndex)
    val mime = format.getString(MediaFormat.KEY_MIME) ?: throw Exception("MIME not found")

    val codec = MediaCodec.createDecoderByType(mime)
    codec.configure(format, null, null, 0)
    codec.start()

    val outputPCM = ArrayList<Short>()
    val bufferInfo = MediaCodec.BufferInfo()

    val timeoutUs = 10000L
    var sawInputEOS = false
    var sawOutputEOS = false

    while (!sawOutputEOS) {
        if (!sawInputEOS) {
            val inputBufferIndex = codec.dequeueInputBuffer(timeoutUs)
            if (inputBufferIndex >= 0) {
                val inputBuffer = codec.getInputBuffer(inputBufferIndex)!!
                val sampleSize = extractor.readSampleData(inputBuffer, 0)
                if (sampleSize < 0) {
                    codec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
                    sawInputEOS = true
                } else {
                    val presentationTimeUs = extractor.sampleTime
                    codec.queueInputBuffer(inputBufferIndex, 0, sampleSize, presentationTimeUs, 0)
                    extractor.advance()
                }
            }
        }

        val outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, timeoutUs)
        if (outputBufferIndex >= 0) {
            val outputBuffer = codec.getOutputBuffer(outputBufferIndex)!!
            outputBuffer.order(ByteOrder.LITTLE_ENDIAN)

            val shortBuffer = outputBuffer.asShortBuffer()
            val chunk = ShortArray(shortBuffer.remaining())
            shortBuffer.get(chunk)
            outputPCM.addAll(chunk.toList())

            codec.releaseOutputBuffer(outputBufferIndex, false)

            if (bufferInfo.flags and MediaCodec.BUFFER_FLAG_END_OF_STREAM != 0) {
                sawOutputEOS = true
            }
        }
    }

    codec.stop()
    codec.release()
    extractor.release()

    // Convert to FloatArray normalized to [-1.0f, 1.0f]
    return outputPCM.map { (it / 32767.0f).coerceIn(-1f, 1f) }.toFloatArray()
}
