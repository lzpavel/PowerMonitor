package com.lzpavel.powermonitor.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun DeviceSelector(
    deviceType: Int = 0,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        val deviceTypeString: String = when (deviceType) {
            1 -> "OnePlus7ProLineage"
            else -> "Debug"
        }
        Text(
            text = "Device: $deviceTypeString",
            modifier = Modifier.clickable { onClick() },
            fontSize = 16.sp
        )
    }
}
