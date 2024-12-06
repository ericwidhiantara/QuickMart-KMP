package com.luckyfrog.quickmart.features.general.presentation.splash

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.luckyfrog.quickmart.core.app.AppPreferences
import com.luckyfrog.quickmart.core.app.MainViewModel
import com.luckyfrog.quickmart.core.resources.Images
import com.luckyfrog.quickmart.utils.resource.route.AppScreen
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel = koinViewModel<MainViewModel>(),
    navController: NavController,

    ) {
    val isFirstTime = AppPreferences.getFirstTime(LocalContext.current)
    Log.d("SplashScreen", "isFirstTime: $isFirstTime")
    val token = AppPreferences.getToken(LocalContext.current)

    // This block will be triggered when the SplashScreen composable is first composed
    LaunchedEffect(Unit) {
        Log.i("SplashScreen", "token : $token")
        Log.i("SplashScreen", "is first time : $isFirstTime")
        Log.i("SplashScreen", "token : $token")
        // Simulate a delay for the splash screen (e.g., 2 seconds)
        delay(3000)
        // Navigate to the login screen
        navController.navigate(
            when (isFirstTime) {
                true -> AppScreen.OnboardingScreen.route
                false -> if (token == "") AppScreen.LoginScreen.route else AppScreen.MainScreen.route
                null -> AppScreen.OnboardingScreen.route
            }
        ) {
            // Remove the splash screen from the back stack to prevent returning to it
            popUpTo(AppScreen.SplashScreen.route) { inclusive = true }
        }
    }
    Scaffold { innerPadding ->
        Image(
            painter = painterResource(
                when (mainViewModel.stateApp.theme) {
                    AppTheme.Light -> Images.icSplashDark
                    AppTheme.Dark -> Images.icSplashLight
                    AppTheme.Default -> if (isSystemInDarkTheme()) Images.icSplashLight else Images.icSplashDark
                }
            ),
            contentDescription = "Logo",
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 48.dp)
                .fillMaxSize()
        )

    }

}