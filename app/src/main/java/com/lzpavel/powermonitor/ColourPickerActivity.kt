package com.lzpavel.powermonitor

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.lzpavel.powermonitor.ui.theme.PowerMonitorTheme

class ColourPickerActivity : ComponentActivity() {

    val LOG_TAG = "ColourPickerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerMonitorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Greeting("Android")

                    val controller = rememberColorPickerController()
                    var color = remember {mutableStateOf(Color(0)) }
                    var cnt = remember { mutableStateOf(0) }
                    //var color: Color = Color(256)
                    Column {
                        HsvColorPicker(modifier = Modifier
                            .fillMaxWidth()
                            .height(450.dp)
                            .padding(10.dp),
                            controller = controller,
                            onColorChanged = { colorEnvelope: ColorEnvelope ->
                                val mColor: Color = colorEnvelope.color
                                color.value = mColor
                                val hexCode: String = colorEnvelope.hexCode
                                val fromUser: Boolean = colorEnvelope.fromUser
                                Log.d(LOG_TAG, "$mColor $hexCode $fromUser")
                            })
                        AlphaSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )
                        BrightnessSlider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp),
                            controller = controller,
                        )

                        //MyBox()
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                                .height(35.dp)
                                .background(color = color.value, shape = RoundedCornerShape(16.dp))
                        )
                    }

                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(35.dp)
            .background(color = Color(128, 128, 128), shape = RectangleShape)
    )
}

/*@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PowerMonitorTheme {
        Greeting("Android")
    }
}*/