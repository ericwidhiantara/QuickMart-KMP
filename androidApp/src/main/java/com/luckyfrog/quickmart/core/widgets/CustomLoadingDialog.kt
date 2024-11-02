package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.luckyfrog.quickmart.R

@Composable
fun CustomLoadingDialog(
    showDialog: MutableState<Boolean>,
    message: String = "${stringResource(id = R.string.loading)} \n${stringResource(id = R.string.please_wait)}",
    onDismiss: () -> Unit = { }
) {
    if (showDialog.value) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Transparent)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = message,
                        color = Color.Black,
                        modifier = Modifier.padding(bottom = 8.dp),
                        textAlign = TextAlign.Center,
                    )
                    CircularProgressIndicator() // You can use any loading indicator here
                    Spacer(modifier = Modifier.height(16.dp))

                }
            }
        }
    }
}