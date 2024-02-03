package com.example.card_detect_2

import ai.onnxruntime.OrtSession
import ai.onnxruntime.*
import ai.onnxruntime.extensions.OrtxPackage
import android.content.res.AssetManager
import android.content.res.AssetManager.AssetInputStream
import android.content.res.Resources
import android.content.res.loader.AssetsProvider
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.card_detect_2.ui.theme.Card_detect_2Theme
import kotlinx.coroutines.*
import java.util.*
import java.io.InputStream
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    private var ortEnv: OrtEnvironment = OrtEnvironment.getEnvironment()
    private lateinit var ortSession: OrtSession
    private lateinit var inputImage: ImageView
    private lateinit var outputImage: ImageView
    private lateinit var objectDetectButton: Button
    private lateinit var classes: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Card_detect_2Theme {
                Surface(modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background) {
                    Greeting(name = "yes")
                    GreetingImage()
                }
            }
        }
//        inputImage = findViewById(R.id.imageView1)
//        outputImage = findViewById(R.id.imageView2)
//        objectDetectButton = findViewById(R.id.object_detection_button)
//        inputImage.setImageBitmap(
//            BitmapFactory.decodeStream(readInputImage(assets))
//        );
//        val sessionOptions: OrtSession.SessionOptions = OrtSession.SessionOptions()
//
//        classes = readClasses(resources)
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
    return assets.open("frame_screenshot_29.01.2024.png")
}


@Composable
fun GreetingImage() {
    val image = painterResource(id = R.raw.test_object_detection_0)
    Box(modifier=Modifier.padding(24.dp)) {
        Image(painter = image, contentDescription = null, contentScale = ContentScale.Crop)
    }
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