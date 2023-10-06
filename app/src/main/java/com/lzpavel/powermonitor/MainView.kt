package com.lzpavel.powermonitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.lzpavel.powermonitor.ui.theme.PowerMonitorTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


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
                var cnt = vm?.cnt?.observeAsState()?.value ?: 0

                var openDialog by remember { mutableStateOf(false) }

                Button(onClick = { vm?.cnt?.postValue(++cnt) }) {
                    Text(text = "$cnt")
                }
                Button(onClick = { MainActivity.showWidget() }) {
                    Text(text = "Show Widget")
                }
                Button(onClick = { MainActivity.showWidgetSu() }) {
                    Text(text = "Show Widget SU")
                }
                Button(onClick = { MainActivity.hideWidget() }) {
                    Text(text = "Hide Widget")
                }
                Button(onClick = { MainActivity.showNotification() } ) {
                    Text(text = "Show Notification")
                }
                Button(onClick = { MainActivity.showColourPicker() }) {
                    Text(text = "Show Colour Picker")
                }
                Button(onClick = { openDialog = true }) {
                    Text(text = "Show Dialog")
                }
                if (openDialog) {
                    MinimalDialog() {
                        openDialog = false
                    }
                }



            }
        }
    }
}

@Preview(showBackground = true)
@Composable
//fun MinimalDialog() {
fun MinimalDialog(onDismissRequest: (() -> Unit)? = null) {
    Dialog(onDismissRequest = { onDismissRequest?.invoke() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Text(
                text = "This is a minimal dialog",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAlertlDialog() {
    AlertDialog(onDismissRequest = { /*TODO*/ }) {
        
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertlDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
fun AlertDialogImpl() {
    // ...
    val openAlertDialog = remember { mutableStateOf(false) }

    // ...
    when {
        // ...
        openAlertDialog.value -> {
            AlertlDialogExample(
                onDismissRequest = { openAlertDialog.value = false },
                onConfirmation = {
                    openAlertDialog.value = false
                    println("Confirmation registered") // Add logic here to handle confirmation.
                },
                dialogTitle = "Alert dialog example",
                dialogText = "This is an example of an alert dialog with buttons.",
                icon = Icons.Default.Info
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AlertDialogPreview() {
    // ...
    //val openAlertDialog = remember { mutableStateOf(false) }

    // ...
    //when {
        // ...
        //openAlertDialog.value -> {
    AlertlDialogExample(
        onDismissRequest = { },
        onConfirmation = { },
        dialogTitle = "Alert dialog example",
        dialogText = "This is an example of an alert dialog with buttons.",
        icon = Icons.Default.Info
    )

    //}

}




