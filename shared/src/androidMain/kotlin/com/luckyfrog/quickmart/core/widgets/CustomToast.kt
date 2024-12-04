package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun CustomToast(message: String, type: ToastType) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(message) {
        coroutineScope.launch {
            snackbarHostState.showSnackbar(message)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { data ->
                Snackbar(
                    modifier = Modifier
                        .background(
                            when (type) {
                                ToastType.Error -> Color.Red
                                ToastType.Success -> Color.Green
                                ToastType.Loading -> Color.Yellow
                            }
                        ),
                    containerColor = Color.Transparent,  // to make background color custom
                    contentColor = Color.White  // to change the text color
                ) {
                    Text(text = data.visuals.message, color = Color.White)
                }
            },
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

enum class ToastType {
    Error, Success, Loading
}

@Composable
fun ShowCustomToast() {
    // Example usage
    CustomToast(message = "This is a success message", type = ToastType.Success)
}
