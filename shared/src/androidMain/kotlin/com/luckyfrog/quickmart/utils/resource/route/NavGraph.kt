package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.features.auth.presentation.login.LoginScreen
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterScreen
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

    }
}