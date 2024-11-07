package com.luckyfrog.quickmart.features.auth.presentation.register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.validators.DefaultValidator
import com.luckyfrog.quickmart.core.validators.EmailValidator
import com.luckyfrog.quickmart.core.validators.PasswordValidator
import com.luckyfrog.quickmart.core.validators.isRegisterInputValid
import com.luckyfrog.quickmart.core.widgets.CustomLoadingDialog
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.features.auth.data.models.response.RegisterFormRequestDto
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.luckyfrog.quickmart.utils.resource.theme.colorBlack
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError

@Composable
fun RegisterScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
    registerViewModel: RegisterViewModel = hiltViewModel(),
    tokenManager: TokenManager = TokenManager(LocalContext.current),
) {
    val fullnameController =
        remember { mutableStateOf("Jhon Doe") }
    val usernameController =
        remember { mutableStateOf("jhon_doe") }
    val emailController =
        remember { mutableStateOf("nukenin.konoha@gmail.com") }
    val passwordController = remember { mutableStateOf("12345678") }
    val passwordConfirmController = remember { mutableStateOf("12345678") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var passwordConfirmVisibility: Boolean by remember { mutableStateOf(false) }

    // Collect login result state from the ViewModel
    val registerState by registerViewModel.state.collectAsState()

    val isLoading = registerState is RegisterState.Loading
    val showDialog = remember { mutableStateOf(false) }

    var shouldValidate by remember { mutableStateOf(false) }
    // Create your validators
    val fullnameValidator = DefaultValidator()
    val usernameValidator = DefaultValidator()
    val emailValidator = EmailValidator()
    val passwordValidator = PasswordValidator()
    val confirmPasswordValidator = PasswordValidator()

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 20.dp)
            ) {
                Image(
                    painter = painterResource(
                        when (mainViewModel.stateApp.theme) {
                            AppTheme.Light -> Images.icLogoDark
                            AppTheme.Dark -> Images.icLogoLight
                            AppTheme.Default -> if (isSystemInDarkTheme()) Images.icLogoLight else Images.icLogoDark
                        }
                    ),
                    modifier = Modifier.height(32.dp),
                    contentDescription = "Logo"
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(
                    rememberScrollState()
                ),
        ) {

            Text(
                text = stringResource(R.string.signup),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = stringResource(R.string.already_have_account),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = {
                        // Navigate to Login
                        navController.navigate(AppScreen.LoginScreen.route) {
                            popUpTo(AppScreen.RegisterScreen.route) {
                                inclusive = true
                            }  // Clear back stack
                        }
                    }) {
                        Text(
                            text = stringResource(R.string.login),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
            CustomTextField(
                validator = fullnameValidator,
                errorMessage = if (fullnameController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 2
                ),
                shouldValidate = shouldValidate,
                value = fullnameController.value,
                onValueChange = { newText ->
                    fullnameController.value = newText
                },
                titleLabel = stringResource(R.string.full_name),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.full_name_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
                validator = usernameValidator,
                errorMessage = if (usernameController.value.isEmpty()) stringResource(R.string.field_required) else stringResource(
                    R.string.field_length, 2
                ),
                shouldValidate = shouldValidate,
                value = usernameController.value,
                onValueChange = { newText ->
                    usernameController.value = newText
                },
                titleLabel = stringResource(R.string.username),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.username_placeholder),
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
                titleLabel = stringResource(R.string.password),
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
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                isButtonEnabled = !isLoading,
                buttonText = stringResource(R.string.create_account),
                onClick = {
                    shouldValidate = true

                    if (usernameController.value.isEmpty() || passwordController.value.isEmpty()) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    if (!isRegisterInputValid(
                            fullnameController.value,
                            usernameController.value,
                            emailController.value,
                            passwordController.value,
                            passwordConfirmController.value,
                            fullnameValidator,
                            usernameValidator,
                            emailValidator,
                            passwordValidator,
                            confirmPasswordValidator
                        )
                    ) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    // Create the RegisterFormRequestDto from the user inputs
                    val params = RegisterFormRequestDto(
                        fullname = fullnameController.value,
                        username = usernameController.value,
                        email = emailController.value,
                        password = passwordController.value,
                        confirmPassword = passwordConfirmController.value,
                    )

                    Log.d("RegisterScreen", "params: $params")
                    // Trigger the login action
                    registerViewModel.register(params)
                }
            )
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                buttonText = stringResource(R.string.signup_with_google),
                buttonTextColor = colorBlack,
                isWithIcon = true,
                buttonIcon = painterResource(
                    Images.icLogoGoogle
                ),
                buttonContainerColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {

                }
            )
            Spacer(
                modifier = Modifier.height(81.dp)
            )

            // Handling the login result (success or failure)
            when (val state = registerState) {

                is RegisterState.Success -> {
                    showDialog.value = false

                    tokenManager.saveToken(state.data.accessToken ?: "")
                    tokenManager.saveRefreshToken(state.data.refreshToken ?: "")
                    // Register success, navigate to the next screen
                    navController.navigate(AppScreen.EmailVerificationScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

                is RegisterState.Error -> {
                    showDialog.value = false
                    Log.d("RegisterScreen", "Error: ${state.message}")
                    // Show a toast with the error message
                    // on below line we are displaying a custom toast message on below line
                    SweetError(
                        message = state.message,
                        duration = Toast.LENGTH_SHORT,
                        padding = PaddingValues(top = 16.dp),
                        contentAlignment = Alignment.TopCenter
                    )
                }

                is RegisterState.Loading -> {
                    showDialog.value = true // Show loading dialog
                    CustomLoadingDialog(
                        showDialog = showDialog,
                    )
                }

                is RegisterState.Idle -> {
                    showDialog.value = false
                    // No action yet
                }

            }
        }
    }

}

