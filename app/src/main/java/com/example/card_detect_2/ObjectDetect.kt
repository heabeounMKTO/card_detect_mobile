package com.example.card_detect_2

import ai.onnxruntime.OnnxJavaType
import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream
import java.nio.ByteBuffer
import java.util.Collections


internal data class Result (
    var outputBitmap: Bitmap,
    var outputBox: Array<FloatArray>
) {}


internal class ObjectDetect() {
    fun detect(inputStream: InputStream, ortEnv: OrtEnvironment, ortSession: OrtSession): Result {
        val rawImageBytes = inputStream.readBytes()
        val shape = longArrayOf(rawImageBytes.size.toLong())
        // loads as uint8
        val inputTensor = OnnxTensor.createTensor(
            ortEnv,
            ByteBuffer.wrap(rawImageBytes),
            shape,
            OnnxJavaType.UINT8
        )

        inputTensor.use {
            val output = ortSession.run(Collections.singletonMap("image", inputTensor),
                setOf("image_out", "scaled_box_out_next")
            )

            output.use {
                val rawOutput = (output?.get(0)?.value) as ByteArray
                val boxOutput = (output?.get(1)?.value) as Array<FloatArray>
                val outputImageBitmap = byteArrayToBitmap(rawOutput)

                var result = Result(outputImageBitmap, boxOutput)
                return result
            }
        }
    }
}

private fun byteArrayToBitmap(data: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(data, 0, data.size)
}