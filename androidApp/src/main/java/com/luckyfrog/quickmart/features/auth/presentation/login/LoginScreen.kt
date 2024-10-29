package com.luckyfrog.quickmart.features.auth.presentation.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.features.auth.data.models.response.LoginFormRequestDto
import com.luckyfrog.quickmart.utils.TokenManager
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.luckyfrog.quickmart.utils.resource.theme.colorBlack
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetError
import com.talhafaki.composablesweettoast.util.SweetToastUtil.SweetSuccess

@Composable
fun LoginScreen(
    mainViewModel: MainViewModel,
    loginViewModel: LoginViewModel = hiltViewModel(),
    navController: NavController,
    tokenManager: TokenManager = TokenManager(LocalContext.current),
) {
    val usernameController =
        remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    // Collect login result state from the ViewModel
    val loginState by loginViewModel.loginState.collectAsState()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 20.dp),
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
            Spacer(modifier = Modifier.height(24.dp))
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
                        text = stringResource(R.string.already_have_account),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    TextButton(onClick = { /*TODO*/ }) {
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
                value = usernameController.value,
                onValueChange = { newText ->
                    usernameController.value = newText
                },
                titleLabel = stringResource(R.string.email),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.email_placeholder),
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
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
                buttonText = stringResource(R.string.login),
                onClick = {
                    // Create the LoginFormRequestDto from the user inputs
                    val loginFormRequest = LoginFormRequestDto(
                        username = usernameController.value,
                        password = passwordController.value,
                        expiresInMins = 30,
                    )
                    Log.d("LoginScreen", "loginFormRequest: $loginFormRequest")
                    // Trigger the login action
                    loginViewModel.login(loginFormRequest)
                }
            )

            // Handling the login result (success or failure)
            when (val state = loginState) {

                is LoginState.Success -> {
                    SweetSuccess(
                        message = "",
                        duration = Toast.LENGTH_LONG,
                        padding = PaddingValues(top = 16.dp),
                        contentAlignment = Alignment.TopCenter
                    )
                    tokenManager.saveToken(state.data.accessToken ?: "")
                    tokenManager.saveRefreshToken(state.data.refreshToken ?: "")
                    // Login success, navigate to the next screen
                    // Navigate to the product list screen and clear the previous stack
                    navController.navigate(AppScreen.MainScreen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }

                is LoginState.Error -> {
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
                    // Show a loading spinner or something similar if you want
                }

                is LoginState.Idle -> {
                    // No action yet
                }
            }
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomOutlinedButton(
                buttonText = stringResource(R.string.login_with_google),
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
            Text(
                text = stringResource(R.string.login_terms_and_conditions),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,

                )
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
            append(stringResource(id = R.string.login_terms_and_conditions))
        }
        append("Eric ")
        withStyle(
            MaterialTheme.typography.bodyMedium.toSpanStyle().copy(fontWeight = FontWeight.Bold)
        ) {
            append("Widhi ")
        }
        withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
            append("Antara")
        }
    }



    Text(
        text = "$annotated",
        modifier = Modifier
            .background(Color.Green, shape = RoundedCornerShape(12.dp))
            .padding(16.dp)
            .clickable { Log.d("lol", "clicked") },

        )
}