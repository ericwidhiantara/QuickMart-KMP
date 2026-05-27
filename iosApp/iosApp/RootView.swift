//
//  RootView.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    @State private var rootView: AppScreen = .splash
    @State private var email: String = ""
    @State private var otpId: String = ""

    var body: some View {
        NavigationStack {
            switch rootView {
            case .splash:
                SplashView(rootView: $rootView)
            case .onboarding:
                OnboardingView(rootView: $rootView)
            case .login:
                LoginView(rootView: $rootView)
            case .register:
                RegisterView(rootView: $rootView)
            case .verify_email:
                EmailVerificationView(rootView: $rootView)
            case .forgot_password_confirm_email:
                EmailConfirmationView(rootView: $rootView)
            case .forgot_password_verify_code:
                VerifyCodeView(rootView: $rootView, email: $email)
            case .create_password:
                CreatePasswordView(rootView: $rootView, otpId: $otpId)
            case .password_created:
                PasswordCreatedView(rootView: $rootView)
            case .main:
                BottomNavBar(rootView: $rootView)
            case .check_password:
                CheckPasswordView(rootView: $rootView)
            case .change_password:
                ChangePasswordView(rootView: $rootView)
            case .wishlist:
                MyWishlistView(rootView: $rootView)
            case .cart:
                MyCartView(rootView: $rootView)
            case .category:
                CategoryView(rootView: $rootView)
            case .product:
                ProductView(rootView: $rootView)
            case .product_detail(let productId):
                ProductDetailView(rootView: $rootView, productId: productId)
            case .product_by_category(let categoryId, let categoryName):
                ProductListByCategoryView(rootView: $rootView, categoryId: categoryId, categoryName: categoryName)
            case .search:
                SearchView(rootView: $rootView)
            }
        }
        .appTheme(.default)  // or .light, .dark
    }
}

#Preview {
    ContentView()
}
