package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.validators.EmailValidator
import com.luckyfrog.quickmart.core.widgets.CustomLoadingDialog
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.auth.data.models.response.ForgotPasswordSendOTPFormRequestDto
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess

@Composable
fun ForgotPasswordEmailConfirmationScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
) {
    val emailController =
        remember { mutableStateOf("") }

    val viewState by viewModel.state.collectAsState()
    val isLoading = viewState is ForgotPasswordState.Loading
    val showDialog = remember { mutableStateOf(false) }

    var shouldValidate by remember { mutableStateOf(false) }
    val emailValidator = EmailValidator()
    when (val state = viewState) {

        is ForgotPasswordState.Success -> {
            showDialog.value = false
            // Show a toast with the success message
            // on below line we are displaying a custom toast message on below line
            SweetSuccess(
                message = state.data.message ?: "",
                duration = Toast.LENGTH_LONG,
                padding = PaddingValues(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            )
            
            // this will using asynchronous to navigate to the next screen
            LaunchedEffect(Unit) {
                navController.navigate(AppScreen.ForgotPasswordVerifyCodeScreen.route)
            }
        }

        is ForgotPasswordState.Error -> {
            showDialog.value = false
            // Show a toast with the error message
            // on below line we are displaying a custom toast message on below line
            SweetError(
                message = state.message,
                duration = Toast.LENGTH_SHORT,
                padding = PaddingValues(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            )
            Log.d("ForgotPasswordEmailConfirmationScreen", "Error State: $state")

        }

        is ForgotPasswordState.Loading -> {
            showDialog.value = true // Show loading dialog
            CustomLoadingDialog(
                showDialog = showDialog,
            )
        }

        is ForgotPasswordState.Idle -> {
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
                validator = emailValidator,
                errorMessage = if (emailController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_email,
                ),
                shouldValidate = shouldValidate,
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
                isButtonEnabled = !isLoading && emailController.value.isNotEmpty() && emailValidator.validate(
                    emailController.value
                ),
                buttonText = stringResource(R.string.send),
                onClick = {
                    shouldValidate = true

                    if (emailController.value.isEmpty() || !emailValidator.validate(emailController.value)) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    val params = ForgotPasswordSendOTPFormRequestDto(
                        email = emailController.value,
                    )
                    viewModel.sendOTP(params)

                }
            )

        }
    }
}