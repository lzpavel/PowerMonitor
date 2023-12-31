package com.lzpavel.powermonitor.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lzpavel.powermonitor.ComponentController
import com.lzpavel.powermonitor.MainViewModel

@Preview(showBackground = true)
@Composable
fun FloatingWidgetSwitcher(vm: MainViewModel? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        var rChecked = false
        val vmChecked = vm?.isStartedFloatingWidgetServiceLive?.observeAsState()?.value
        if (vmChecked != null) {
            rChecked = vmChecked
        }
        Text(
            text = "Floating widget",
            fontSize = 16.sp
        )
        Switch(
            checked = rChecked,
            onCheckedChange = null,
            modifier = Modifier.clickable {
                ComponentController.mainActivity?.switchService()
            }
        )
    }
}