package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luckyfrog.ecommerce.core.app.AppPreferences.ACCESS_TOKEN
import com.luckyfrog.ecommerce.core.app.MainViewModel
import com.luckyfrog.ecommerce.features.auth.presentation.login.LoginScreen
import com.luckyfrog.ecommerce.features.auth.presentation.login.UserViewModel
import com.luckyfrog.ecommerce.features.general.presentation.BottomNavBar
import com.luckyfrog.ecommerce.features.settings.presentation.SettingsScreen
import io.paperdb.Paper

@Composable
fun NavGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (Paper.book()
                .read(ACCESS_TOKEN, "")!! == ""
        ) AppScreen.LoginScreen.route else AppScreen.MainScreen.route
    ) {
        composable(route = AppScreen.LoginScreen.route) {
            LoginScreen(
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