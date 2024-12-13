//
//  LoginView.swift
//  iosApp
//
//  Created by Eric on 12/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI
import Shared


struct LoginView: View {
    @Binding var rootView : AppScreen
    @ObservedObject var viewModel: LoginViewModel = KoinHelper().getLoginViewModel()

    @State private var username: String = ""
    @State private var password: String = ""
    @State private var isPasswordVisible: Bool = false
    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    
    @Environment(\.colorScheme) var colorScheme
    @Environment(\.presentationMode) var presentationMode
    
   
    
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .center, spacing: 16) {
                    // Logo
                    Image(colorScheme == .dark ? "LogoLight" : "LogoDark")
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(height: 32)
                    
                    // Title and Signup Link
                    Text("Login")
                        .font(.title)
                        .fontWeight(.bold)
                    
                    HStack {
                        Text("Don't have an account?")
                        
                        NavigationLink(destination: OnboardingView(rootView:$rootView)) {
                            Text("Sign Up")
                                .foregroundColor(.blue)
                        }
                    }
                    
                    // Username TextField
                    CustomTextField(
                        title: "Username",
                        placeholder: "Enter username",
                        text: $username,
                        shouldValidate: $shouldValidate,
                        validator: { value in
                            !value.isEmpty && value.count >= 2
                        },
                        errorMessage: "Username is required (min 2 characters)"
                    )
                    
                    // Password TextField
                    CustomPasswordField(
                        title: "Password",
                        placeholder: "Enter password",
                        text: $password,
                        shouldValidate: $shouldValidate,
                        isVisible: $isPasswordVisible,
                        validator: { value in
                            !value.isEmpty && value.count >= 6
                        },
                        errorMessage: "Password is required (min 6 characters)"
                    )
                    
                    // Forgot Password
                    HStack {
                        Spacer()
                        NavigationLink(destination: SplashView(rootView:$rootView)) {
                            Text("Forgot Password?")
                                .foregroundColor(.blue)
                        }
                    }
                    
                    // Login Button
                    Button(action: performLogin) {
                        Text("Login")
                            .frame(maxWidth: .infinity)
                            .padding()
                            .background(Color.blue)
                            .foregroundColor(.white)
                            .cornerRadius(10)
                    }
//                    .disabled(viewModel.loginState is LoginState.Loading)
                    
                    // Google Login Button
                    Button(action: performGoogleLogin) {
                        HStack {
                            Image("LogoGoogle")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 24, height: 24)
                            Text("Login with Google")
                                .foregroundColor(.black)
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.white)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(Color.gray.opacity(0.3), lineWidth: 1)
                        )
                    }
                    
                    // Terms and Privacy
                    TermsAndPrivacyText()
                }
                .padding()
            }
            .navigationBarHidden(true)
        }
        .onChange(of: viewModel.loginState) { state in
            handleLoginState(state)
        }
        .alert(isPresented: $showErrorDialog) {
            Alert(
                title: Text("Login Error"),
                message: Text("Error"),
                dismissButton: .default(Text("OK"))
            )
        }
    }
    
    private func performLogin() {
        shouldValidate = true
        
        guard !username.isEmpty, !password.isEmpty else {
            showErrorDialog = true
            return
        }
        
        let loginRequest = LoginFormRequestDto(
            username: username,
            password: password
        )
        
        viewModel.login(params: loginRequest)
    }
    
    private func performGoogleLogin() {
        // Implement Google login logic
        // For now, using the same login method
        performLogin()
    }
    
    private func handleLoginState(_ state: LoginState) {
        switch state {
        case is LoginState.Success:
            // Navigate to main screen
            // You'll need to implement navigation logic based on your app's navigation structure
            break
        case let errorState as LoginState.Error:
            showErrorDialog = true
        default:
            break
        }
    }
    
    private func extractErrorMessage() -> String {
        if case LoginState.Error() = viewModel.loginState {
            return self
        }
        return "Unknown error occurred"
    }
}

// Custom TextField with Validation
struct CustomTextField: View {
    let title: String
    let placeholder: String
    @Binding var text: String
    @Binding var shouldValidate: Bool
    let validator: (String) -> Bool
    let errorMessage: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(.caption)
            
            TextField(placeholder, text: $text)
                .textFieldStyle(RoundedBorderTextFieldStyle())
            
            if shouldValidate && !validator(text) {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(.caption)
            }
        }
    }
}

// Custom Password Field with Visibility Toggle
struct CustomPasswordField: View {
    let title: String
    let placeholder: String
    @Binding var text: String
    @Binding var shouldValidate: Bool
    @Binding var isVisible: Bool
    let validator: (String) -> Bool
    let errorMessage: String
    
    var body: some View {
        VStack(alignment: .leading) {
            Text(title)
                .font(.caption)
            
            HStack {
                Group {
                    if isVisible {
                        TextField(placeholder, text: $text)
                    } else {
                        SecureField(placeholder, text: $text)
                    }
                }
                .textFieldStyle(RoundedBorderTextFieldStyle())
                
                Button(action: { isVisible.toggle() }) {
                    Image(systemName: isVisible ? "eye.slash" : "eye")
                        .foregroundColor(.gray)
                }
            }
            
            if shouldValidate && !validator(text) {
                Text(errorMessage)
                    .foregroundColor(.red)
                    .font(.caption)
            }
        }
    }
}

// Terms and Privacy Text
struct TermsAndPrivacyText: View {
    var body: some View {
        HStack{
            Text("By continuing, you agree to our ")
            Text("Privacy Policy").foregroundColor(.blue)
                .onTapGesture {
                    // Handle Privacy Policy tap
                }
            Text(" and ")
            Text("Terms and Conditions").foregroundColor(.blue)
                .onTapGesture {
                    // Handle Terms and Conditions tap
                }
        }
    }
}
