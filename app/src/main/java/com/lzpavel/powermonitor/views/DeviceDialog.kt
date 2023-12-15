package com.lzpavel.powermonitor.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.lzpavel.powermonitor.ComponentController
import com.lzpavel.powermonitor.device.Device

@Preview(showBackground = true)
@Composable
fun DeviceDialog(
    onConfirmDismiss: () -> Unit = {}
) {
    Dialog(onDismissRequest = onConfirmDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Debug",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            Device.current = Device.DEBUG
                            onConfirmDismiss()
                                   },
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Divider()
                Text(
                    text = "OnePlus7ProLineage",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .clickable {
                            Device.current = Device.ONE_PLUS_7_PRO_LINEAGE
                            onConfirmDismiss()
                                   },
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}