package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.luckyfrog.quickmart.core.widgets.CustomLoadingDialog
import com.luckyfrog.quickmart.core.widgets.CustomOTPInput
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordVerifyOTPFormRequestDto
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation.ForgotPasswordEmailConfirmationViewModel
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@Composable
fun ForgotPasswordVerifyCodeScreen(
    navController: NavController,
    timerDuration: Long = 60000L, // 60 seconds for the timer
    email: String,
    viewModel: ForgotPasswordVerifyCodeViewModel = koinViewModel<ForgotPasswordVerifyCodeViewModel>(),
    emailViewModel: ForgotPasswordEmailConfirmationViewModel = koinViewModel<ForgotPasswordEmailConfirmationViewModel>(),
) {
    val viewState by viewModel.state.collectAsState()

    val isLoading = viewState is ForgotPasswordVerifyCodeState.Loading
    val showDialog = remember { mutableStateOf(false) }
    var isTimerFinished by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableLongStateOf(timerDuration / 1000) }
    var timerKey by remember { mutableIntStateOf(0) }  // Unique key to reset the timer
    var otpCode by remember { mutableStateOf("") }
    // Timer logic with LaunchedEffect
    LaunchedEffect(key1 = timerKey) {
        isTimerFinished = false
        remainingTime = timerDuration / 1000 // Reset the timer duration

        while (remainingTime > 0) {
            delay(1000L) // 1 second delay
            remainingTime -= 1
        }
        isTimerFinished = true
    }

    // Function to format remaining time as MM:ss
    val formattedTime = String.format(
        Locale.US,
        "%02d:%02d",
        remainingTime / 60,      // minutes
        remainingTime % 60       // seconds
    )

    val params = ForgotPasswordSendOTPFormRequestDto(
        email = email,
    )

    when (val state = viewState) {

        is ForgotPasswordVerifyCodeState.Success -> {
            showDialog.value = false

            LaunchedEffect(Unit) {
                navController.navigate(AppScreen.CreatePasswordScreen.route + "?otp_id=${state.data.otpId}") {
                    popUpTo(AppScreen.ForgotPasswordVerifyCodeScreen.route) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }

            }

        }

        is ForgotPasswordVerifyCodeState.Error -> {
            showDialog.value = false
            // Show a toast with the error message
            // on below line we are displaying a custom toast message on below line
            SweetError(
                message = state.message,
                duration = Toast.LENGTH_SHORT,
                padding = PaddingValues(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            )
            Log.d("EmailVerificationScreen", "Error State: $state")

        }

        is ForgotPasswordVerifyCodeState.Loading -> {
            showDialog.value = true // Show loading dialog
            CustomLoadingDialog(
                showDialog = showDialog,
            )
        }

        is ForgotPasswordVerifyCodeState.Idle -> {
            // No action yet
        }


    }


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
                            append("02")
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
                text = stringResource(R.string.email_verification),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.enter_verification_code),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomOTPInput(
                otpLength = 6,
                onOtpEntered = { code ->
                    otpCode = code.trim()
                    // Handle OTP submission
                    Log.d("OTP", "Entered OTP: $otpCode")
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isTimerFinished) {
                TextButton(
                    onClick = {
                        // Resend OTP logic
                        emailViewModel.sendOTP(params)

                        timerKey++  // Change the key to restart LaunchedEffect
                    },
                    modifier = Modifier.align(
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    Text(
                        text = stringResource(R.string.resend_code),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            } else {
                // Show the countdown timer
                Text(
                    text = stringResource(R.string.resend_code_timer) + " $formattedTime",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                isButtonEnabled = !isLoading && otpCode.length == 6,
                buttonText = stringResource(R.string.proceed),
                onClick = {
                    if (otpCode.length != 6) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    val form = ForgotPasswordVerifyOTPFormRequestDto(
                        otpCode = otpCode,
                        email = email,
                    )
                    viewModel.verifyOTP(form)
                }
            )

        }
    }
}