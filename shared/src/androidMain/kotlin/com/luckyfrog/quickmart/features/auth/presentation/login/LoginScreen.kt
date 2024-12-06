package com.luckyfrog.quickmart.features.auth.presentation.login

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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.AppPreferences
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.validators.DefaultValidator
import com.luckyfrog.quickmart.core.validators.PasswordValidator
import com.luckyfrog.quickmart.core.validators.isLoginInputValid
import com.luckyfrog.quickmart.core.widgets.CustomLoadingDialog
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.features.auth.data.models.request.LoginFormRequestDto
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.luckyfrog.quickmart.utils.resource.theme.colorBlack
import com.luckyfrog.quickmart.utils.resource.theme.colorBlue
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    loginViewModel: LoginViewModel = koinViewModel<LoginViewModel>(),
    navController: NavController,

    ) {
    val usernameController =
        remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    // Collect login result state from the ViewModel
    val loginState by loginViewModel.loginState.collectAsState()

    val isLoading = loginState is LoginState.Loading
    val showDialog = remember { mutableStateOf(false) }

    var shouldValidate by remember { mutableStateOf(false) }
// Create your validators
    val usernameValidator = DefaultValidator()
    val passwordValidator = PasswordValidator()


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
                text = stringResource(R.string.login),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = stringResource(R.string.dont_have_account),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = {
                        // Navigate to RegisterScreen
                        navController.navigate(AppScreen.RegisterScreen.route) {
                            popUpTo(AppScreen.LoginScreen.route) {
                                inclusive = true
                            }  // Clear back stack
                        }
                    }) {
                        Text(
                            text = stringResource(R.string.signup),
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))
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
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            TextButton(
                onClick = {
                    navController.navigate(AppScreen.ForgotPasswordEmailConfirmationScreen.route)
                },
                modifier = Modifier.align(
                    alignment = Alignment.End
                )
            ) {
                Text(
                    text = stringResource(R.string.forgot_password),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                isButtonEnabled = !isLoading,
                buttonText = stringResource(R.string.login),
                onClick = {
                    shouldValidate = true

                    if (usernameController.value.isEmpty() || passwordController.value.isEmpty()) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    if (!isLoginInputValid(
                            usernameController.value,
                            passwordController.value,
                            usernameValidator,
                            passwordValidator
                        )
                    ) {
                        showDialog.value = true
                        return@CustomOutlinedButton
                    }

                    // Create the LoginFormRequestDto from the user inputs
                    val loginFormRequest = LoginFormRequestDto(
                        username = usernameController.value,
                        password = passwordController.value,
                    )

                    Log.d("LoginScreen", "loginFormRequest: $loginFormRequest")
                    // Trigger the login action
                    loginViewModel.login(loginFormRequest)

                }
            )

            // Handling the login result (success or failure)
            when (val state = loginState) {

                is LoginState.Success -> {
                    showDialog.value = false
                    AppPreferences.setToken(state.data.accessToken ?: "", LocalContext.current)
                    AppPreferences.setRefreshToken(
                        state.data.refreshToken ?: "",
                        LocalContext.current
                    )
//                    tokenManager.saveToken(state.data.accessToken ?: "")
//                    tokenManager.saveRefreshToken(state.data.refreshToken ?: "")
                    // Login success, navigate to the next screen
                    // Don't forget to use LaunchedEffect to navigate
                    // Because if we don't use LaunchedEffect,
                    // the mainscreen will be infinite loop request
                    LaunchedEffect(Unit) {
                        navController.navigate(AppScreen.MainScreen.route) {
                            popUpTo(AppScreen.LoginScreen.route) {
                                inclusive = true
                            }  // Clear back stack
                        }
                    }
                }

                is LoginState.Error -> {
                    showDialog.value = false
                    Log.d("LoginScreen", "Error: ${state.message}")
                    // Show a toast with the error message
                    // on below line we are displaying a custom toast message on below line
                    SweetError(
                        message = state.message,
                        duration = Toast.LENGTH_SHORT,
                        padding = PaddingValues(top = 16.dp),
                        contentAlignment = Alignment.TopCenter
                    )
                }

                is LoginState.Loading -> {
                    showDialog.value = true // Show loading dialog
                    CustomLoadingDialog(
                        showDialog = showDialog,
                    )
                }

                is LoginState.Idle -> {
                    showDialog.value = false
                    // No action yet
                }
            }
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                isButtonEnabled = !isLoading,
                buttonText = stringResource(R.string.login_with_google),
                buttonTextColor = colorBlack,
                isWithIcon = true,
                buttonIcon = painterResource(
                    Images.icLogoGoogle
                ),
                buttonContainerColor = MaterialTheme.colorScheme.onPrimary,
                onClick = {
                    // Create the LoginFormRequestDto from the user inputs
                    val loginFormRequest = LoginFormRequestDto(
                        username = usernameController.value,
                        password = passwordController.value,
                    )

                    Log.d("LoginScreen", "loginFormRequest: $loginFormRequest")
                    // Trigger the login action
                    loginViewModel.login(loginFormRequest)

                }
            )
            Spacer(
                modifier = Modifier.height(61.dp)
            )
            LoginTermsAndPrivacyText()
            Spacer(
                modifier = Modifier.height(16.dp)
            )
        }
    }

}


@Composable
fun LoginTermsAndPrivacyText() {
    val annotated = buildAnnotatedString {
        withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
            append(stringResource(id = R.string.login_terms_and_conditions) + " ")
        }
        withLink(
            link = LinkAnnotation.Clickable(
                tag = "TAG",
                linkInteractionListener = {
                    Log.d("LoginTermsAndPrivacyText", "privacy_policy")
                },
            ),
        ) {
            withStyle(
                MaterialTheme.typography.bodyMedium.toSpanStyle()
                    .copy(fontWeight = FontWeight.Bold, color = colorBlue)
            ) {
                append(stringResource(id = R.string.privacy_policy))
            }
        }
        withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
            append(" " + stringResource(id = R.string.and) + " ")
        }

        withLink(
            link = LinkAnnotation.Clickable(
                tag = "TAG",
                linkInteractionListener = {
                    Log.d("LoginTermsAndPrivacyText", "terms_conditions")
                },
            ),
        ) {
            withStyle(
                MaterialTheme.typography.bodyMedium.toSpanStyle()
                    .copy(fontWeight = FontWeight.Bold, color = colorBlue)
            ) {
                append(stringResource(id = R.string.terms_and_conditions))
            }
        }
    }

    Text(
        text = annotated,
        modifier = Modifier
            .padding(16.dp),
        textAlign = TextAlign.Center
    )
}