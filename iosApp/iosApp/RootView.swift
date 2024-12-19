//
//  RootView.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct ContentView: View {
    @State private var rootView: AppScreen = .login
    @State private var email: String = ""

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
            case .main:
                Text("app_name")
            }
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .appTheme(.default)  // or .light, .dark
    }
}

#Preview {
    ContentView()
}
