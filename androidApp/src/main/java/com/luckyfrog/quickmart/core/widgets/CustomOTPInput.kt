package com.luckyfrog.quickmart.core.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import android.view.KeyEvent as AndroidKeyEvent

@Composable
fun CustomOTPInput(
    otpLength: Int = 6,
    onOtpEntered: (String) -> Unit
) {
    var otpValue by remember { mutableStateOf(List(otpLength) { "" }) }  // Use a list to represent each input
    val focusRequesters = List(otpLength) { FocusRequester() }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until otpLength) {
            OutlinedTextField(
                value = otpValue[i],
                onValueChange = { value ->
                    if (value.length <= 1) {
                        // Update OTP value for the current index
                        otpValue = otpValue.toMutableList().apply { this[i] = value }.toList()

                        // Move to next or previous box based on input
                        when {
                            value.isNotEmpty() && i < otpLength - 1 -> focusRequesters[i + 1].requestFocus()
                            value.isEmpty() && i > 0 -> focusRequesters[i - 1].requestFocus()
                        }

                        // Call the callback with the updated OTP value
                        onOtpEntered(otpValue.joinToString(""))
                    }
                },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp)
                    .focusRequester(focusRequesters[i])
                    .onKeyEvent { event ->
                        if (event.nativeKeyEvent.keyCode == AndroidKeyEvent.KEYCODE_DEL &&
                            event.type == KeyEventType.KeyUp &&
                            otpValue[i].isEmpty() && i > 0
                        ) {
                            // Move focus to previous when deleting empty input
                            focusRequesters[i - 1].requestFocus()
                            true
                        } else false
                    },
                singleLine = true,
                textStyle = TextStyle(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }
    }
}
