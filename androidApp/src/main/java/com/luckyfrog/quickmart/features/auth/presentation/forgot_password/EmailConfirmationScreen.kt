package com.luckyfrog.quickmart.features.auth.presentation.forgot_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.core.widgets.CustomTopBar

@Composable
fun EmailConfirmationScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    timerDuration: Long = 60000L // 60 seconds for the timer
) {
    val emailController =
        remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CustomTopBar(
                title = stringResource(R.string.email_verification),
                navController = navController,
                actions = {
                    val annotatedString = buildAnnotatedString {
                        withStyle(
                            MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        ) {
                            append("01")
                        }
                        withStyle(
                            MaterialTheme.typography.bodyMedium.toSpanStyle().copy(
                                fontSize = 14.sp
                            )
                        ) {
                            append("/03")
                        }
                    }
                    Text(text = annotatedString)
                }
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.confirm_email),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.confirm_desc),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                value = emailController.value,
                onValueChange = { newText ->
                    emailController.value = newText
                },
                titleLabel = stringResource(R.string.email),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.email_placeholder),
            )
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                buttonText = stringResource(R.string.send),
                onClick = {
                }
            )

        }
    }
}