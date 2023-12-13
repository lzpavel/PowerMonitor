package com.lzpavel.powermonitor.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lzpavel.powermonitor.ui.theme.PowerMonitorTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.lzpavel.powermonitor.ComponentController
import com.lzpavel.powermonitor.MainViewModel


@Preview(showBackground = true)
@Composable
fun MainView(vm: MainViewModel? = null) {

    PowerMonitorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                var isOpenColorDialog by remember { mutableStateOf(false) }
                var isOpenDeviceDialog by remember { mutableStateOf(false) }

                var textSizeFloatingWidget =
                    vm?.textSizeFloatingWidgetLive?.observeAsState()?.value ?: 16F
                var textColorFloatingWidget =
                    vm?.textColorFloatingWidgetLive?.observeAsState()?.value ?: Color.Blue.toArgb()
                var deviceType =
                    vm?.deviceTypeLive?.observeAsState()?.value ?: 0
                //var currentColor by remember { mutableStateOf(Color.Blue) }
                //var colorStyle = vm?.floatingWidgetStyleLive?.observeAsState()?.value

                //var isFwShowing = vm?.isFloatingWidgetShowing?.observeAsState()?.value ?: false
                FloatingWidgetSwitcher(vm)
                Divider()
                ColorSelector(textColorFloatingWidget) {
                    isOpenColorDialog = true
                }
                Divider()
                SizeSelector(textSizeFloatingWidget)
                Divider()
                DeviceSelector(deviceType) {
                    isOpenDeviceDialog = true
                }
                Divider()
                Button(onClick = { ComponentController.mainActivity?.saveSettings() }) {
                    Text(text = "Save config")
                }
                Button(onClick = { ComponentController.mainActivity?.testSu() }) {
                    Text(text = "Test Su")
                }
                Button(onClick = { ComponentController.mainActivity?.exitApp() }) {
                    Text(text = "Exit")
                }
                if (isOpenColorDialog) {
                    ColorPickerDialog {
                        isOpenColorDialog = false
                    }
                }
                if (isOpenDeviceDialog) {
                    DeviceDialog() {
                        isOpenDeviceDialog = false
                    }
                }


            }
        }
    }
}




