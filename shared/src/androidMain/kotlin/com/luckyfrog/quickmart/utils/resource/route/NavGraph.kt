package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.auth.presentation.email_verification.EmailVerificationScreen
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.PasswordCreatedScreen
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.create_password.CreatePasswordScreen
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.email_confirmation.ForgotPasswordEmailConfirmationScreen
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.verify_code.ForgotPasswordVerifyCodeScreen
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginScreen
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterScreen
import com.luckyfrog.quickmart.features.general.presentation.main.BottomNavBar
import com.luckyfrog.quickmart.features.general.presentation.onboarding.OnboardingScreen
import com.luckyfrog.quickmart.features.general.presentation.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.SplashScreen.route
    ) {

        // GENERAL
        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(
                navController = navController
            )
        }
        composable(route = AppScreen.OnboardingScreen.route) {
            OnboardingScreen(
                navController = navController
            )
        }

        composable(route = AppScreen.MainScreen.route) {
            BottomNavBar(
                navController = navController,
            )
        }

        /// AUTH
        composable(route = AppScreen.LoginScreen.route) {
            LoginScreen(
                navController = navController
            )
        }
        composable(route = AppScreen.RegisterScreen.route) {
            RegisterScreen(
                navController = navController
            )
        }
        composable(route = AppScreen.EmailVerificationScreen.route) {
            EmailVerificationScreen(
                navController = navController
            )
        }

        composable(route = AppScreen.ForgotPasswordEmailConfirmationScreen.route) {
            ForgotPasswordEmailConfirmationScreen(
                navController = navController
            )
        }

        composable(route = AppScreen.ForgotPasswordVerifyCodeScreen.route + "?email={email}") {
            ForgotPasswordVerifyCodeScreen(
                navController = navController,
                email = it.arguments?.getString("email") ?: "",
            )
        }

        composable(route = AppScreen.CreatePasswordScreen.route + "?otp_id={otpId}") {
            CreatePasswordScreen(
                navController = navController,
                otpId = it.arguments?.getString("otpId") ?: "",
            )
        }

        composable(route = AppScreen.PasswordCreatedScreen.route) {
            PasswordCreatedScreen(
                navController = navController
            )
        }


    }
}