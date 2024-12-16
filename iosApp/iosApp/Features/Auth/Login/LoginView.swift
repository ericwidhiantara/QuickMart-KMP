//
//  LoginView.swift
//  iosApp
//
//  Created by Eric on 12/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct LoginView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: LoginViewModel = KoinHelper()
        .getLoginViewModel()

    @State var uiState: LoginState = LoginState.Idle()

    @State private var username: String = ""
    @State private var password: String = ""
    @State private var isPasswordVisible: Bool = false
    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    @State private var showLoadingDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""

    @Environment(\.colorScheme) var colorScheme

    private func handleLoginState(_ state: LoginState) {
        switch state {
        case is LoginState.Success:
            showSnackbar = false
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                rootView = .main
            }
            break
        case let errorState as LoginState.Error:
            showSnackbar = true
            snackbarMessage = errorState.message
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                showSnackbar = false
            }
            break
        default:
            showSnackbar = false
            break
        }
    }

    var body: some View {

        let appUiState = viewModel.loginState

        VStack {
            ScrollView {
                VStack(
                    alignment: .leading,
                    spacing: 16
                ) {
                    
                    // Title and Signup Link
                    Text("login")
                        .font(.title)
                        .fontWeight(.bold)
                    HStack {
                        Text("dont_have_account").font(.system(size: 14))
                        
                        Button(action: {
                            // Implement Skip action
                        }) {
                            Text(
                                NSLocalizedString(
                                    "signup",
                                    comment: "Signup")
                            ).font(.system(size: 14))
                                .foregroundColor(.colorCyan)
                        }
                    }
                    Spacer().frame(height: 32)
                    
                    // Username TextField
                    CustomTextField(
                        type: .text,
                        titleLabel: NSLocalizedString(
                            "username", comment: ""),
                        value: $username,
                        validator: { $0.count >= 3 },
                        shouldValidate: shouldValidate,
                        placeholder: NSLocalizedString(
                            "username_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    )
                    // Password TextField
                    CustomTextField(
                        type: .password,
                        titleLabel: NSLocalizedString(
                            "password", comment: ""),
                        value: $password,
                        validator: { $0.count >= 8 },
                        shouldValidate: shouldValidate,
                        placeholder: NSLocalizedString(
                            "password_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    )
                    Spacer().frame(height: 12)
                    HStack {
                        Spacer()
                        Button(action: {
                            // Implement Skip action
                        }) {
                            Text(
                                NSLocalizedString(
                                    "forgot_password",
                                    comment: "Forgot Password")
                            )
                            .foregroundColor(.colorCyan)
                        }
                    }
                    Spacer().frame(height: 16)
                    
                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "login",
                            comment: "Login"),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {
                            //                            guard !username.isEmpty, !password.isEmpty
                            //                            else {
                            //                                shouldValidate = true
                            //                                return
                            //                            }
                            
                            let loginRequest = LoginFormRequestDto(
                                username: username,
                                password: password
                            )
                            appUiState.subscribe { state in
                                self.uiState = state!
                            }
                            viewModel.login(params: loginRequest)
                        }
                    )
                    .disabled(
                        uiState is LoginState.Loading)
                    Button(action: {}) {
                        HStack {
                            Image("LogoGoogle")
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 24, height: 24)
                            Text("login_with_google")
                                .foregroundColor(.black)
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.white)
                        .overlay(
                            RoundedRectangle(cornerRadius: 10)
                                .stroke(
                                    Color.gray.opacity(0.3),
                                    lineWidth: 1)
                        )
                    }
                    Spacer()
                    LoginTermsAndPrivacyText()
                        .onOpenURL { url in
                            switch url.absoluteString {
                            case "privacy":
                                print("Privacy Policy Tapped")
                                // Handle privacy policy navigation
                            case "terms":
                                print("Terms and Conditions Tapped")
                                // Handle terms and conditions navigation
                            default:
                                break
                            }
                        }
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 20)
            }
            .onChange(of: uiState) { state in
                handleLoginState(state)
            }
            .task {
                appUiState.subscribe { state in
                    self.uiState = state!
                }
            }

            .modifier(
                CustomActivityIndicatorModifier(
                    isLoading: uiState is LoginState.Loading)
            ).snackbar(
                show: $showSnackbar, bgColor: .red, txtColor: .white,
                icon: "xmark", iconColor: .white,
                message: snackbarMessage
            )
            
        }.toolbar {
            ToolbarItem(placement: .topBarLeading) {
                Image(colorScheme == .dark ? "LogoLight" : "LogoDark")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(height: 32)

            }
        }

    }
}

struct LoginTermsAndPrivacyText: View {
    private let loginTerms = "login_terms_and_conditions"
    private let termsAndConditions = "terms_and_conditions"
    private let privacyPolicy = "privacy_policy"
    private let and = "and"

    var body: some View {
        Text(attributedString)
            .padding(16)
            .multilineTextAlignment(.center)
    }

    private var attributedString: AttributedString {
        var fullString = AttributedString()

        // Regular text
        var regularAttributes = AttributeContainer()
        regularAttributes.font = .body

        // Clickable link attributes
        var linkAttributes = AttributeContainer()
        linkAttributes.font = .body.weight(.bold)
        linkAttributes.foregroundColor = .colorCyan

        // Construct the attributed string
        fullString += AttributedString(
            NSLocalizedString(loginTerms, comment: ""),
            attributes: regularAttributes)

        var privacyLink = AttributedString(
            NSLocalizedString(privacyPolicy, comment: ""),
            attributes: linkAttributes)
        privacyLink.link = URL(string: "privacy")
        fullString += privacyLink

        fullString += AttributedString(
            " " + and + " ", attributes: regularAttributes)

        var termsLink = AttributedString(
            NSLocalizedString(termsAndConditions, comment: ""),
            attributes: linkAttributes)
        termsLink.link = URL(string: "terms")
        fullString += termsLink

        return fullString
    }
}

#Preview {
    LoginView(rootView: .constant(.login))
}
