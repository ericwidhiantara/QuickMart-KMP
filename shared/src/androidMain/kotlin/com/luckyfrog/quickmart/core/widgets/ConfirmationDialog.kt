package com.luckyfrog.quickmart.core.widgets

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.luckyfrog.quickmart.R

@Composable
fun ConfirmationDialog(
    title: String = stringResource(id = R.string.confirm_delete),
    description: String = stringResource(id = R.string.confirm_delete_desc),
    confirmText: String = stringResource(id = R.string.delete),
    cancelText: String = stringResource(id = R.string.cancel),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text(text = title) },
        text = { Text(text = description, color = MaterialTheme.colorScheme.onSurface) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = cancelText)
            }
        }
    )
}
