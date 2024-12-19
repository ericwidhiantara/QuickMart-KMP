//
//  RegisterView.swift
//  iosApp
//
//  Created by Eric on 16/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct RegisterView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: RegisterViewModel = KoinHelper()
        .getRegisterViewModel()

    @State var uiState: RegisterState = RegisterState.Idle()

    @State private var fullname: String = "Jhon Doe"
    @State private var username: String = "jhon"
    @State private var email: String = "crashcyber20@gmail.com"
    @State private var password: String = "!_Ash008"
    @State private var passwordConfirm: String = "!_Ash008"
    @State private var isPasswordVisible: Bool = false
    @State private var isPasswordConfirmVisible: Bool = false
    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    @State private var showLoadingDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isRegisterSuccess: Bool? = false

    @Environment(\.colorScheme) var colorScheme

    private func handleRegisterState(_ state: RegisterState) {
        switch state {
        case let success as RegisterState.Success:
            showSnackbar = false
            _ = AppPreferences.shared.setToken(success.data.accessToken ?? "")
            _ = AppPreferences.shared.setRefreshToken(
                success.data.refreshToken ?? "")
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                self.isRegisterSuccess = true
                //                rootView = .main
            }
            break
        case let errorState as RegisterState.Error:
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

        let appUiState = viewModel.state

        VStack {
            ScrollView {

                VStack(
                    alignment: .leading,
                    spacing: 16
                ) {

                    Text("signup")
                        .font(.title)
                        .fontWeight(.bold)
                    HStack {
                        Text("already_have_account").font(.system(size: 14))

                        Button(action: {
                            rootView = .login
                        }) {
                            Text(
                                "login"
                            ).font(.system(size: 14))
                                .foregroundColor(.colorCyan)
                        }
                    }
                    Spacer().frame(height: 32)

                    CustomTextField(
                        type: .text,
                        titleLabel: NSLocalizedString(
                            "full_name", comment: ""),
                        value: $fullname,
                        validator: { $0.count >= 3 },
                        shouldValidate: shouldValidate,
                        placeholder: NSLocalizedString(
                            "full_name_placeholder", comment: ""),
                        textInputAutocapitalization: .words
                    )

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

                    CustomTextField(
                        type: .text,
                        titleLabel: NSLocalizedString(
                            "email", comment: ""),
                        value: $email,
                        validator: {
                            let emailRegex =
                                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                            let predicate = NSPredicate(
                                format: "SELF MATCHES %@", emailRegex)
                            return predicate.evaluate(with: $0)

                        },
                        shouldValidate: shouldValidate,
                        errorMessage: NSLocalizedString(
                            "field_email", comment: ""),
                        placeholder: NSLocalizedString(
                            "email_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    )

                    CustomTextField(
                        type: .password,
                        titleLabel: NSLocalizedString(
                            "password", comment: ""),
                        value: $password,
                        validator: { password in
                            // Minimum 8 characters, at least one uppercase, one lowercase, and one number
                            let passwordRegex =
                                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
                            let predicate = NSPredicate(
                                format: "SELF MATCHES %@", passwordRegex)
                            return predicate.evaluate(with: password)
                        },
                        shouldValidate: shouldValidate,
                        errorMessage: String(
                            format: NSLocalizedString(
                                "field_password",
                                comment:
                                    "This field must be at least %1$d characters, including at least one uppercase letter, one lowercase letter, and one number"
                            ), 8),
                        placeholder: NSLocalizedString(
                            "password_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    )

                    CustomTextField(
                        type: .password,
                        titleLabel: NSLocalizedString(
                            "confirm_password", comment: ""),
                        value: $passwordConfirm,
                        validator: { $0 == password && $0.count >= 8 },
                        shouldValidate: shouldValidate,
                        errorMessage: NSLocalizedString(
                            "field_confirm_password",
                            comment: "Passwords do not match"),
                        placeholder: NSLocalizedString(
                            "confirm_password_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    )

                    Spacer().frame(height: 16)
                    NavigationLink(
                        destination: EmailVerificationView(rootView: $rootView),
                        tag: true,
                        selection: $isRegisterSuccess
                    ) {
                        EmptyView()
                    }
                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "create_account",
                            comment: "Create Account"
                        ),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {
                            let emailRegex =
                                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                            let emailPredicate = NSPredicate(
                                format: "SELF MATCHES %@", emailRegex)

                            let passwordRegex =
                                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
                            let passwordPredicate = NSPredicate(
                                format: "SELF MATCHES %@", passwordRegex)

                            guard !fullname.isEmpty,
                                !username.isEmpty,
                                !email.isEmpty,
                                emailPredicate.evaluate(with: email),
                                !password.isEmpty,
                                passwordPredicate.evaluate(with: password),
                                !passwordConfirm.isEmpty,
                                password == passwordConfirm
                            else {
                                shouldValidate = true
                                return
                            }

                            let registerRequest = RegisterFormRequestDto(
                                fullname: fullname,
                                username: username,
                                email: email,
                                password: password,
                                confirmPassword: passwordConfirm
                            )

                            appUiState.subscribe { state in
                                self.uiState = state!
                            }
                            viewModel.register(params: registerRequest)
                        }
                    )

                    .disabled(
                        uiState is RegisterState.Loading)

                    Spacer()

                }
                .padding(.horizontal, 16)
                .padding(.vertical, 20)
            }
            .onChange(of: uiState) { state in
                handleRegisterState(state)
            }
            .task {
                appUiState.subscribe { state in
                    self.uiState = state!
                }
            }

            .modifier(
                CustomActivityIndicatorModifier(
                    isLoading: uiState is RegisterState.Loading)
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

#Preview {
    RegisterView(rootView: .constant(.register))
}
