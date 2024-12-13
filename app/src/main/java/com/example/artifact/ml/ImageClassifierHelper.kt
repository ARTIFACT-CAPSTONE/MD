//package com.example.artifact.ml
//
//import android.content.Context
//import android.graphics.Bitmap
//import android.graphics.Color
//import org.tensorflow.lite.DataType
//import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
//
//class ImageClassifierHelper(context: Context, private val model: TFLiteInterpreter) {
//
//    private val inputFeature0: TensorBuffer by lazy {
//        // Define input size and data type based on your model
//        TensorBuffer.createFixedSize(intArrayOf(1, 32, 32, 3), DataType.FLOAT32)
//    }
//
//    fun classifyImage(image: Bitmap): String {
//        preprocessImage(image, inputFeature0)
//        model.run(inputFeature0.buffer)
//
//        val outputFeature0 = model.getOutputTensor(0)
//        val probabilities = outputFeature0.floatArray
//
//        val predictedClass = probabilities.indexOf(probabilities.maxOrNull() ?: 0.0f)
//
//        return "Class: $predictedClass"
//    }
//
//    private fun preprocessImage(image: Bitmap, inputFeature0: TensorBuffer) {
//        val resizedBitmap = Bitmap.createScaledBitmap(image, 32, 32, true)
//        val imageData = FloatArray(32 * 32 * 3)
//        for (y in 0 until 32) {
//            for (x in 0 until 32) {
//                val pixel = resizedBitmap.getPixel(x, y)
//                val red = Color.red(pixel) / 255.0f
//                val green = Color.green(pixel) / 255.0f
//                val blue = Color.blue(pixel) / 255.0f
//                val index = (y * 32 + x) * 3
//                imageData[index] = red
//                imageData[index + 1] = green
//                imageData[index + 2] = blue
//            }
//        }
//        inputFeature0.loadBuffer(imageData)
//    }
//}