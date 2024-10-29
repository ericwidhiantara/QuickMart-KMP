package com.luckyfrog.quickmart.utils.resource.route

sealed class AppScreen(val route: String) {
    data object SplashScreen : AppScreen(ConstantAppScreenName.SPLASH_SCREEN)
    data object OnboardingScreen : AppScreen(ConstantAppScreenName.ONBOARDING_SCREEN)
    data object LoginScreen : AppScreen(ConstantAppScreenName.LOGIN_SCREEN)
    data object MainScreen : AppScreen(ConstantAppScreenName.MAIN_SCREEN)
    data object SettingsScreen : AppScreen(ConstantAppScreenName.SETTINGS_SCREEN)
}

object ConstantAppScreenName {
    const val SPLASH_SCREEN = "splash_screen"
    const val ONBOARDING_SCREEN = "onboarding_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val MAIN_SCREEN = "main_screen"

    const val SETTINGS_SCREEN = "settings_screen"

}