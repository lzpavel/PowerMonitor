package com.lzpavel.powermonitor.views

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColor
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.lzpavel.powermonitor.ComponentController
import com.lzpavel.powermonitor.FloatingWidgetStyle
import com.lzpavel.powermonitor.MainViewModel

@Preview(showBackground = true)
@Composable
fun MyColorPicker() {

    val controller = rememberColorPickerController()

    var initialColor: Color? = null
    ComponentController.mainViewModel?.textColorFloatingWidget?.let {
        initialColor = Color(it)
    }

    Column {
        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
            controller = controller,
            initialColor = initialColor,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                val newColor = colorEnvelope.color
                FloatingWidgetStyle.textColor = newColor.toArgb()

                //ComponentController.mainViewModel?.textColorFloatingWidget = newColor.toArgb()
                //ComponentController.floatingWidgetService?.floatingWidget?.textColor = newColor.toArgb()

                //val color: Color = colorEnvelope.color
                //val hexCode: String = colorEnvelope.hexCode
                //val fromUser: Boolean = colorEnvelope.fromUser
                //Log.d(LOG_TAG, "$mColor $hexCode $fromUser")
            })
        AlphaSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller
        )
        BrightnessSlider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp),
            controller = controller,
            initialColor = initialColor
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp)
                .background(
                    //color = Color(initialColor) ?: Color.Blue,
                    color = controller.selectedColor.value,
                    //color = mInitialColor,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}