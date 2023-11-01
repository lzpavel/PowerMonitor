package com.lzpavel.powermonitor.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.lzpavel.powermonitor.MainViewModel

@Preview(showBackground = true)
@Composable
fun ColorPickerDialog(
    vm: MainViewModel? = null,
    onDismiss: (() -> Unit)? = null,
    onConfirm: (() -> Unit)? = null
) {
    Dialog(onDismissRequest = { onDismiss?.invoke() }) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                        .fillMaxHeight()
            ) {
                MyColorPicker(vm)
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    TextButton(onClick = {
                        onDismiss?.invoke()
                    }) {
                        Text(text = "Cancel")
                    }
                    TextButton(onClick = {
                        onConfirm?.invoke()
                    }) {
                        Text(text = "OK")
                    }
                }
            }
            /*Text(
                text = "This is a minimal dialog",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )*/
        }
    }
}