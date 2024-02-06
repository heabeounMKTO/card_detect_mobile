package com.example.card_detect_2

import ai.onnxruntime.OrtSession
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import ai.onnxruntime.*
import ai.onnxruntime.extensions.OrtxPackage
import android.content.res.AssetManager
import android.content.res.AssetManager.AssetInputStream
import android.content.res.Resources
import android.content.res.loader.AssetsProvider
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RawRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.card_detect_2.ui.theme.Card_detect_2Theme
import kotlinx.coroutines.*
import java.util.*
import java.io.InputStream
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter

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
                    DetectionSection()
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
    return assets.open("frame_screenshot_29.01.2024.png")
}

@Composable
fun DetectionSection(modifier: Modifier = Modifier) {
    var inputImage by remember {
        mutableStateOf<Uri?>(null)
    }

    var outputImage by remember {
        mutableStateOf<Painter?>(null)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent() ,
            onResult = {
                uri -> uri?.let {
                    inputImage = it
            }
            } )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {

        inputImage?.let {
            Image(painter = rememberAsyncImagePainter(model = inputImage), contentDescription = null,
                    modifier = Modifier.size(200.dp))
        }
        Row {
            Button(onClick = { galleryLauncher.launch("image/*") }) {
                Text("load image")
            }
            Button(onClick = { outputImage = painterResource(id = R.raw.test_object_detection_0) }) {
                Text("detect!")
            }
        }

        outputImage?.let {
            Image(painter = rememberAsyncImagePainter(model = outputImage), contentDescription = null,
                modifier = Modifier.size(200.dp))
        }

    }
}