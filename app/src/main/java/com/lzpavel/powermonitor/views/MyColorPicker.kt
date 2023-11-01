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
import com.github.skydoves.colorpicker.compose.AlphaSlider
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import com.lzpavel.powermonitor.MainViewModel

@Preview(showBackground = true)
@Composable
fun MyColorPicker(
    vm: MainViewModel? = null,
    onColorChanged: (() -> Unit)? = null
) {

    val controller = rememberColorPickerController()
    var color = remember { mutableStateOf(Color.Blue) }
//var color: Color = Color(256)
    Column {
        var rColor: Color? = null
        val vmColor = vm?.floatingWidgetStyle?.textColorPre
        //val vmColor = vm?.textColorFloatingWidgetLive?.observeAsState()?.value
        if (vmColor != null) {
            rColor = Color(vmColor)
            //rColor = Color(0x77777777)
        }
        HsvColorPicker(modifier = Modifier
            .fillMaxWidth()
            .height(450.dp)
            .padding(10.dp),
            controller = controller,
            initialColor = rColor,
            onColorChanged = { colorEnvelope: ColorEnvelope ->
                val mColor: Color = colorEnvelope.color
                color.value = mColor
                //vm?.setTextColorFloatingWidget(mColor.toArgb())
                vm?.floatingWidgetStyle?.textColor = mColor.toArgb()
                val hexCode: String = colorEnvelope.hexCode
                val fromUser: Boolean = colorEnvelope.fromUser
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
            initialColor = rColor
        )

        //MyBox()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .height(35.dp)
                .background(
                    color = color.value,
                    shape = RoundedCornerShape(16.dp)
                )
        )
    }
}