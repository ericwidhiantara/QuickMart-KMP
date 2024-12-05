package com.luckyfrog.quickmart.features.auth.presentation.forgot_password.create_password

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.validators.PasswordValidator
import com.luckyfrog.quickmart.core.widgets.CustomLoadingDialog
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.auth.data.models.request.ForgotPasswordChangePasswordFormRequestDto
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreatePasswordScreen(
    navController: NavController,
    otpId: String,
    viewModel: CreatePasswordViewModel = koinViewModel<CreatePasswordViewModel>(),
) {
    val passwordController = remember { mutableStateOf("") }
    val passwordConfirmController = remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var passwordConfirmVisibility: Boolean by remember { mutableStateOf(false) }

    // Collect login result state from the ViewModel
    val viewState by viewModel.state.collectAsState()

    val isLoading = viewState is CreatePasswordState.Loading
    val showDialog = remember { mutableStateOf(false) }

    var shouldValidate by remember { mutableStateOf(false) }
    // Create your validators
    val passwordValidator = PasswordValidator()

    when (val state = viewState) {

        is CreatePasswordState.Success -> {
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
                navController.navigate(AppScreen.PasswordCreatedScreen.route) {
                    popUpTo(AppScreen.CreatePasswordScreen.route) {
                        inclusive = true
                    }
                }
            }
        }

        is CreatePasswordState.Error -> {
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

        is CreatePasswordState.Loading -> {
            showDialog.value = true // Show loading dialog
            CustomLoadingDialog(
                showDialog = showDialog,
            )
        }

        is CreatePasswordState.Idle -> {
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
                            append("03")
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
                text = stringResource(R.string.new_password),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.new_password_desc),
                fontSize = 14.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                validator = passwordValidator,
                errorMessage = if (passwordController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 6
                ),
                shouldValidate = shouldValidate,
                value = passwordController.value,
                onValueChange = { newText ->
                    passwordController.value = newText
                },
                titleLabel = stringResource(R.string.new_password),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.password_placeholder),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description =
                        if (passwordVisibility) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                validator = passwordValidator,
                errorMessage = if (passwordController.value.isEmpty()) stringResource(R.string.field_required) else if (passwordController.value != passwordConfirmController.value) stringResource(
                    R.string.field_confirm_password,
                ) else stringResource(R.string.field_length, 6),
                shouldValidate = shouldValidate,
                value = passwordConfirmController.value,
                onValueChange = { newText ->
                    passwordConfirmController.value = newText
                },
                titleLabel = stringResource(R.string.confirm_password),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.confirm_password_placeholder),
                visualTransformation = if (passwordConfirmVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordConfirmVisibility)
                        Icons.Filled.Visibility
                    else Icons.Filled.VisibilityOff

                    // Please provide localized description for accessibility services
                    val description =
                        if (passwordConfirmVisibility) "Hide password" else "Show password"

                    IconButton(onClick = {
                        passwordConfirmVisibility = !passwordConfirmVisibility
                    }) {
                        Icon(imageVector = image, description)
                    }
                }
            )
            Spacer(modifier = Modifier.height(24.dp))
            CustomOutlinedButton(
                isButtonEnabled = !isLoading,
                buttonText = stringResource(R.string.save),
                onClick = {
                    shouldValidate = true

                    if (passwordController.value.isEmpty() || passwordConfirmController.value.isEmpty()) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }
                    
                    // Create the RegisterFormRequestDto from the user inputs
                    val params = ForgotPasswordChangePasswordFormRequestDto(
                        otpId = otpId,
                        newPassword = passwordController.value,
                        confirmPassword = passwordConfirmController.value
                    )

                    // Trigger the login action
                    viewModel.changePassword(params)

                }
            )

        }
    }
}