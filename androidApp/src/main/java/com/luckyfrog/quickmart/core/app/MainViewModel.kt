package com.luckyfrog.quickmart.core.app

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme

class MainViewModel: ViewModel() {

    var stateApp by mutableStateOf(MainState())

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ThemeChange -> {
                stateApp = stateApp.copy(theme = event.theme)
            }
        }
    }

}

sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme) : MainEvent()
}

data class MainState(
    val theme: AppTheme = AppPreferences.getTheme(),
)

fun logout(navController: NavController) {
    // Remove token
//    Paper.book().delete(ACCESS_TOKEN)
//    Paper.book().delete(REFRESH_TOKEN)

    // Navigate to LoginScreen
    navController.navigate(AppScreen.LoginScreen.route) {
        popUpTo(AppScreen.LoginScreen.route) { inclusive = true }  // Clear back stack
    }
}