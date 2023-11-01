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
import com.lzpavel.powermonitor.MainViewModel


@Preview(showBackground = true)
@Composable
fun MainView(
    vm: MainViewModel? = null,
    onButtonExit: (() -> Unit)? = null,
    onClickFloatingWidgetSwitcher: (() -> Unit)? = null,
) {

    PowerMonitorTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                var cnt = vm?.cnt?.observeAsState()?.value ?: 0

                var openColorDialog by remember { mutableStateOf(false) }
                //var currentColor by remember { mutableStateOf(Color.Blue) }
                //var colorStyle = vm?.floatingWidgetStyleLive?.observeAsState()?.value

                var isFwShowing = vm?.isFloatingWidgetShowing?.observeAsState()?.value ?: false
                FloatingWidgetSwitcher(
                    isFwShowing,
                    onClick = onClickFloatingWidgetSwitcher
                )
                Divider()
                ColorSelector(vm) {
                    openColorDialog = true
                }
                Divider()
                Button(onClick = { vm?.cnt?.postValue(++cnt) }) {
                    Text(text = "$cnt")
                }
                Button(onClick = { onButtonExit?.invoke() }) {
                    Text(text = "Exit")
                }
                if (openColorDialog) {
                    ColorPickerDialog(
                        vm,
                        onDismiss = {
                            vm?.floatingWidgetStyle?.let {
                                it.textColor = it.textColorPre
                            }
                            openColorDialog = false
                                    },
                        onConfirm = {
                            vm?.floatingWidgetStyle?.let {
                                it.textColorPre = it.textColor
                            }
                            openColorDialog = false
                        }
                    )
                }


            }
        }
    }
}




