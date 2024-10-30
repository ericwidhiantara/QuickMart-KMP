package com.luckyfrog.quickmart.features.auth.presentation.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.R
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.core.widgets.CustomOutlinedButton
import com.luckyfrog.quickmart.core.widgets.CustomTextField
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import com.luckyfrog.quickmart.utils.resource.theme.colorBlack

@Composable
fun RegisterScreen(
    mainViewModel: MainViewModel,
    navController: NavController,
) {
    val fullNameController =
        remember { mutableStateOf("") }
    val emailController =
        remember { mutableStateOf("") }
    val passwordController = remember { mutableStateOf("") }
    val passwordConfirmController = remember { mutableStateOf("") }
    var passwordVisibility: Boolean by remember { mutableStateOf(false) }
    var passwordConfirmVisibility: Boolean by remember { mutableStateOf(false) }

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
                        // Navigate to LoginScreen
                        navController.navigate(AppScreen.LoginScreen.route) {
                            popUpTo(AppScreen.LoginScreen.route) {
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
                value = fullNameController.value,
                onValueChange = { newText ->
                    fullNameController.value = newText
                },
                titleLabel = stringResource(R.string.full_name),
                titleLabelFontSize = 12.sp,
                placeholder = stringResource(R.string.full_name_placeholder),
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
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextField(
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
                buttonText = stringResource(R.string.create_account),
                onClick = {

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

        }
    }

}

