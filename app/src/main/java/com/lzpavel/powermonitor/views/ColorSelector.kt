package com.lzpavel.powermonitor.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lzpavel.powermonitor.FloatingWidgetStyle
import com.lzpavel.powermonitor.MainViewModel

@Preview(showBackground = true)
@Composable
fun ColorSelector(
    vm: MainViewModel? = null,
    onClick: (() -> Unit)? = null
) {
//fun ColorSelector(color: Color = Color.Blue, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick?.invoke() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Select color",
            fontSize = 16.sp,
            modifier = Modifier.clickable { onClick?.invoke() }
        )
        var rColor: Color = Color.Blue
        val vmColor = vm?.textColorFloatingWidgetLive?.observeAsState()?.value
        if (vmColor != null) {
            rColor = Color(vmColor)
        }
        Box(
            modifier = Modifier
                .height(24.dp)
                .width(24.dp)
                .background(
                    color = rColor,
                    shape = RectangleShape
                )
                .clickable { onClick?.invoke() }
        )
        /*Switch(
            checked = isChecked,
            onCheckedChange = null,
            modifier = Modifier.clickable { onClick?.invoke() }
        )*/
    }
}