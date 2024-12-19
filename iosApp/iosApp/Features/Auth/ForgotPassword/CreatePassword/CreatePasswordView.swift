//
//  EmailConfirmationView.swift
//  iosApp
//
//  Created by Eric on 19/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct CreatePasswordView: View {
    @Binding var rootView: AppScreen
    @Binding var otpId: String
    @ObservedObject var viewModel: CreatePasswordViewModel =
        KoinHelper()
        .getCreatePasswordViewModel()

    @State var uiState: CreatePasswordState =
        CreatePasswordState.Idle()

    @State private var password: String = "!_Ash008"
    @State private var passwordConfirm: String = "!_Ash008"
    @State private var isPasswordVisible: Bool = false
    @State private var isPasswordConfirmVisible: Bool = false

    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    @State private var showLoadingDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isConfirmSuccess: Bool? = false
    @State private var isSuccessSnackbar: Bool = false

    @Environment(\.colorScheme) var colorScheme

    private func handleState(_ state: CreatePasswordState) {
        switch state {
        case is CreatePasswordState.Success:
//            showSnackbar = true
//            isSuccessSnackbar = true
//            snackbarMessage = success.data.message ?? ""

            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                self.isConfirmSuccess = true
                showSnackbar = false

            }
            break
        case let errorState as CreatePasswordState.Error:
            showSnackbar = true
            isSuccessSnackbar = false
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

        GeometryReader { geometry in
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {

                    VStack(alignment: .leading, spacing: 8) {
                        Text("new_password")
                            .font(.title)
                            .fontWeight(.bold)

                        Text("new_password_desc")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .padding(.top, 20)
                    .padding(.horizontal)

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
                    ).padding(.horizontal)

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
                    ).padding(.horizontal)

                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "save",
                            comment: ""
                        ),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {
                            let passwordRegex =
                                "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$"
                            let passwordPredicate = NSPredicate(
                                format: "SELF MATCHES %@", passwordRegex)

                            guard
                                !password.isEmpty,
                                passwordPredicate.evaluate(with: password),
                                !passwordConfirm.isEmpty,
                                password == passwordConfirm
                            else {
                                shouldValidate = true
                                return
                            }

                            let params =
                                ForgotPasswordChangePasswordFormRequestDto(
                                    otpId: otpId,
                                    newPassword: password,
                                    confirmPassword: passwordConfirm
                                )

                            appUiState.subscribe { state in
                                self.uiState = state!
                            }
                            viewModel.changePassword(params: params)
                        }
                    ).padding(.horizontal)

                        .disabled(
                            uiState is CreatePasswordState.Loading)

                    Spacer()
                }
                .frame(minHeight: geometry.size.height)

            }

            .scrollDisabled(true)
        }
        .onAppear {

            viewModel.state.subscribe { state in
                if let state = state {
                    self.uiState = state
                }
            }

        }
        .onChange(of: uiState) { state in
            handleState(state)
        }
        .modifier(
            CustomActivityIndicatorModifier(
                isLoading: uiState is CreatePasswordState.Loading)
        )
        .snackbar(
            show: $showSnackbar, bgColor: isSuccessSnackbar ? .green : .red,
            txtColor: .white,
            icon: "xmark", iconColor: .white,
            message: snackbarMessage
        )
    }
}

#Preview {
    RegisterView(rootView: .constant(.register))
}
