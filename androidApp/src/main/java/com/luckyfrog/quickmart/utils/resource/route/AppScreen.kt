package com.luckyfrog.quickmart.utils.resource.route

sealed class AppScreen(val route: String) {
    /// GENERAL
    data object SplashScreen : AppScreen(ConstantAppScreenName.SPLASH_SCREEN)
    data object OnboardingScreen : AppScreen(ConstantAppScreenName.ONBOARDING_SCREEN)
    data object MainScreen : AppScreen(ConstantAppScreenName.MAIN_SCREEN)

    /// AUTH
    data object LoginScreen : AppScreen(ConstantAppScreenName.LOGIN_SCREEN)
    data object RegisterScreen : AppScreen(ConstantAppScreenName.REGISTER_SCREEN)
    data object EmailVerificationScreen : AppScreen(ConstantAppScreenName.EMAIL_VERIFICATION_SCREEN)
    data object ForgotPasswordEmailConfirmationScreen :
        AppScreen(ConstantAppScreenName.FORGOT_PASSWORD_EMAIL_CONFIRMATION_SCREEN)

    data object ForgotPasswordVerifyCodeScreen :
        AppScreen(ConstantAppScreenName.FORGOT_PASSWORD_VERIFY_CODE_SCREEN)

    data object CreatePasswordScreen : AppScreen(ConstantAppScreenName.CREATE_PASSWORD_SCREEN)
    data object PasswordCreatedScreen : AppScreen(ConstantAppScreenName.PASSWORD_CREATED_SCREEN)

    /// HOME
    data object HomeScreen : AppScreen(ConstantAppScreenName.HOME_SCREEN)


    /// PRODUCT
    data object ProductListScreen : AppScreen(ConstantAppScreenName.PRODUCT_LIST_SCREEN)
    data object ProductDetailScreen : AppScreen(ConstantAppScreenName.PRODUCT_DETAIL_SCREEN)


    /// PROFILE
    data object SettingsScreen : AppScreen(ConstantAppScreenName.SETTINGS_SCREEN)
}

object ConstantAppScreenName {
    /// GENERAL
    const val SPLASH_SCREEN = "splash_screen"
    const val ONBOARDING_SCREEN = "onboarding_screen"
    const val MAIN_SCREEN = "main_screen"

    ///  AUTH
    const val LOGIN_SCREEN = "login_screen"
    const val REGISTER_SCREEN = "register_screen"
    const val EMAIL_VERIFICATION_SCREEN = "email_verification_screen"
    const val FORGOT_PASSWORD_EMAIL_CONFIRMATION_SCREEN =
        "forgot_password_email_confirmation_screen"
    const val FORGOT_PASSWORD_VERIFY_CODE_SCREEN = "forgot_password_verify_code_screen"
    const val CREATE_PASSWORD_SCREEN = "create_password_screen"
    const val PASSWORD_CREATED_SCREEN = "password_created_screen"

    /// HOME
    const val HOME_SCREEN = "home_screen"

    /// PRODUCT
    const val PRODUCT_LIST_SCREEN = "product_list_screen"
    const val PRODUCT_DETAIL_SCREEN = "product_detail_screen"

    /// PROFILE
    const val SETTINGS_SCREEN = "settings_screen"

}