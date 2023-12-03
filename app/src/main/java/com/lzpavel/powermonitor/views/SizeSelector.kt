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
import com.lzpavel.powermonitor.ComponentController

@Preview(showBackground = true)
@Composable
fun SizeSelector(size: Float = 16F) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Text size: $size",
            fontSize = 16.sp
        )
        Slider(
            value = size,
            onValueChange = {v ->
                ComponentController.floatingWidgetService?.floatingWidget?.textSize = v
                ComponentController.mainViewModel?.textSizeFloatingWidget = v
            },
            steps = 0,
            valueRange = 5f..100f
        )
    }
}