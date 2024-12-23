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
        NavigationView {
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
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .appTheme(.default)  // or .light, .dark
    }
}

#Preview {
    ContentView()
}
