package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.auth.presentation.email_verification.EmailVerificationScreen
import com.luckyfrog.quickmart.features.auth.presentation.forgot_password.EmailConfirmationScreen
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginScreen
import com.luckyfrog.quickmart.features.auth.presentation.login.UserViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterScreen
import com.luckyfrog.quickmart.features.general.presentation.main.BottomNavBar
import com.luckyfrog.quickmart.features.general.presentation.onboarding.OnboardingScreen
import com.luckyfrog.quickmart.features.general.presentation.splash.SplashScreen
import com.luckyfrog.quickmart.features.settings.presentation.SettingsScreen

@Composable
fun NavGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.LoginScreen.route
    ) {
        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(route = AppScreen.OnboardingScreen.route) {
            OnboardingScreen(
                mainViewModel = mainViewModel,
                navController = navController,

                )
        }
        composable(route = AppScreen.LoginScreen.route) {
            LoginScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(route = AppScreen.RegisterScreen.route) {
            RegisterScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(route = AppScreen.EmailVerificationScreen.route) {
            EmailVerificationScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }
        composable(route = AppScreen.EmailConfirmationScreen.route) {
            EmailConfirmationScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(route = AppScreen.MainScreen.route) {
            BottomNavBar(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }


        composable(route = AppScreen.SettingsScreen.route) {
            val userViewModel = hiltViewModel<UserViewModel>()

            SettingsScreen(
                userViewModel = userViewModel, navController = navController
            )
        }
    }
}