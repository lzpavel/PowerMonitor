package com.lzpavel.powermonitor.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview

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