package com.lzpavel.powermonitor.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lzpavel.powermonitor.MainViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun SizeSelector(vm: MainViewModel? = null) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        //val size by remember { mutableStateOf(0.5f) }
        //var size by remember { mutableFloatStateOf(5f) }
        var rSize = 16f
        val vmSize = vm?.textSizeFloatingWidgetLive?.observeAsState()?.value
        if (vmSize != null) {
            rSize = vmSize
        }
        Text(
            text = "Text size: $rSize",
            fontSize = 16.sp
        )
        Slider(
            value = rSize,
            onValueChange = {v ->
                vm?.floatingWidgetStyle?.textSize = v
                            },
            steps = 0,
            valueRange = 5f..100f
        )
    }
}