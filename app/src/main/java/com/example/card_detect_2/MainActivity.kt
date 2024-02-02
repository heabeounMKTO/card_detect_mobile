package com.example.card_detect_2

import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.content.res.AssetManager
import android.content.res.AssetManager.AssetInputStream
import android.content.res.Resources
import android.content.res.loader.AssetsProvider
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.card_detect_2.ui.theme.Card_detect_2Theme
import kotlinx.coroutines.*
import java.util.*
import java.io.InputStream


class MainActivity : ComponentActivity() {
    private var ortEnv: OrtEnvironment = OrtEnvironment.getEnvironment()
    private lateinit var OrtSession: OrtSession
    private lateinit var inputImage: ImageView
    private lateinit var outputImage: ImageView
    private lateinit var objectDetectButton: Button
    private lateinit var clases: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Card_detect_2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

private fun readClasses(res: Resources): List<String> {
    return res.openRawResource(R.raw.classes).bufferedReader().readLines()
}

private fun readModel(res: Resources): ByteArray {
    val modelID = R.raw.yolov8n_with_pre_post_processing
    return res.openRawResource(modelID).readBytes()
}

private fun readInputImage(assets: AssetManager): InputStream {
    return assets.open("test_object_detection_0.jpg")
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Card_detect_2Theme {
        Greeting("Android")
    }
}