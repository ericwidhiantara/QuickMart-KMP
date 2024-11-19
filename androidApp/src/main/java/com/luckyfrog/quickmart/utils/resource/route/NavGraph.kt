package com.luckyfrog.quickmart.utils.resource.route

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.luckyfrog.quickmart.features.auth.presentation.login.UserViewModel
import com.luckyfrog.quickmart.features.auth.presentation.register.RegisterScreen
import com.luckyfrog.quickmart.features.category.presentation.categories.CategoryListScreen
import com.luckyfrog.quickmart.features.general.presentation.main.BottomNavBar
import com.luckyfrog.quickmart.features.general.presentation.onboarding.OnboardingScreen
import com.luckyfrog.quickmart.features.general.presentation.splash.SplashScreen
import com.luckyfrog.quickmart.features.home.presentation.dashboard.HomeScreen
import com.luckyfrog.quickmart.features.product.presentation.product_detail.ProductDetailScreen
import com.luckyfrog.quickmart.features.product.presentation.product_list.ProductListScreen
import com.luckyfrog.quickmart.features.product.presentation.product_list_by_category.ProductListByCategoryScreen
import com.luckyfrog.quickmart.features.product.presentation.search.SearchScreen
import com.luckyfrog.quickmart.features.settings.presentation.SettingsScreen

@Composable
fun NavGraph(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreen.SplashScreen.route
    ) {
        /// GENERAL
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

        composable(route = AppScreen.MainScreen.route) {

            BottomNavBar(
                mainViewModel = mainViewModel,
                navController = navController,
            )
        }

        /// AUTH
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

        composable(route = AppScreen.ForgotPasswordEmailConfirmationScreen.route) {
            ForgotPasswordEmailConfirmationScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        composable(route = AppScreen.ForgotPasswordVerifyCodeScreen.route + "?email={email}") {
            ForgotPasswordVerifyCodeScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                email = it.arguments?.getString("email") ?: "",
            )
        }

        composable(route = AppScreen.CreatePasswordScreen.route + "?otp_id={otpId}") {
            CreatePasswordScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                otpId = it.arguments?.getString("otpId") ?: "",
            )
        }

        composable(route = AppScreen.PasswordCreatedScreen.route) {
            PasswordCreatedScreen(
                mainViewModel = mainViewModel,
                navController = navController
            )
        }

        /// HOME
        composable(route = AppScreen.HomeScreen.route) {
            val userViewModel = hiltViewModel<UserViewModel>()

            HomeScreen(
                mainViewModel = mainViewModel,
                navController = navController,
                userViewModel = userViewModel
            )
        }

        /// PRODUCT
        composable(route = AppScreen.ProductListScreen.route + "?title={title}") {
            val title = it.arguments?.getString("title")
                ?: ""

            ProductListScreen(
                mainViewModel = mainViewModel, navController = navController,
                topBarTitle = title,
            )
        }

        composable(route = AppScreen.ProductListByCategoryScreen.route + "?id={id}&title={title}") {
            val id = it.arguments?.getString("id")
                ?: ""
            val title = it.arguments?.getString("title")
                ?: ""
            ProductListByCategoryScreen(
                mainViewModel = mainViewModel, navController = navController,
                topBarTitle = title,
                categoryId = id,
            )
        }

        composable(
            route = AppScreen.ProductDetailScreen.route + "/{productId}", // Assuming your route contains a productId
        ) {
            val productId = it.arguments?.getString("productId")
                ?: ""

            ProductDetailScreen(
                productId = productId,
                navController = navController
            )
        }

        composable(route = AppScreen.SearchScreen.route) {
            SearchScreen(
                mainViewModel = mainViewModel, navController = navController
            )
        }

        /// CATEGORY
        composable(route = AppScreen.CategoryListScreen.route) {
            CategoryListScreen(
                mainViewModel = mainViewModel, navController = navController
            )
        }

        /// PROFILE
        composable(route = AppScreen.SettingsScreen.route) {
            val userViewModel = hiltViewModel<UserViewModel>()

            SettingsScreen(
                userViewModel = userViewModel, navController = navController
            )
        }
    }
}