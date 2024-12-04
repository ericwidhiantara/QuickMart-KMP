package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.presentation.login.LoginScreen

@Composable
fun NavGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.LoginScreen.route
    ) {

        /// AUTH
        composable(route = AppScreen.LoginScreen.route) {
            LoginScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

    }
}