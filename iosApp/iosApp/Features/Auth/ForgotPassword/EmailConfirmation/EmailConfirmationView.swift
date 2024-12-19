//
//  EmailConfirmationView.swift
//  iosApp
//
//  Created by Eric on 19/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct EmailConfirmationView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: ForgotPasswordEmailConfirmationViewModel =
        KoinHelper()
        .getForgotPasswordEmailConfirmationViewModel()

    @State var uiState: ForgotPasswordEmailConfirmationState =
        ForgotPasswordEmailConfirmationState.Idle()

    @State private var email: String = "crashcyber20@gmail.com"

    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    @State private var showLoadingDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isConfirmSuccess: Bool? = false
    @State private var isSuccessSnackbar: Bool = false

    @Environment(\.colorScheme) var colorScheme

    private func handleState(_ state: ForgotPasswordEmailConfirmationState) {
        switch state {
        case let success as ForgotPasswordEmailConfirmationState.Success:
            showSnackbar = true
            isSuccessSnackbar = true
            snackbarMessage = success.data.message ?? ""

            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                self.isConfirmSuccess = true
                showSnackbar = false

            }
            break
        case let errorState as ForgotPasswordEmailConfirmationState.Error:
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
                        Text("confirm_email")
                            .font(.title)
                            .fontWeight(.bold)

                        Text("confirm_desc")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .padding(.top, 20)
                    .padding(.horizontal)

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
                    ).padding(.horizontal)

                    NavigationLink(
                        destination: VerifyCodeView(rootView: $rootView, email: $email),
                        tag: true,
                        selection: $isConfirmSuccess
                    ) {
                        EmptyView()
                    }
                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "send",
                            comment: ""
                        ),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {
                            let emailRegex =
                                "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
                            let emailPredicate = NSPredicate(
                                format: "SELF MATCHES %@", emailRegex)

                            guard
                                !email.isEmpty,
                                emailPredicate.evaluate(with: email)

                            else {
                                shouldValidate = true
                                return
                            }

                            let params = ForgotPasswordSendOTPFormRequestDto(
                                email: email
                            )

                            appUiState.subscribe { state in
                                self.uiState = state!
                            }
                            viewModel.sendOTP(params: params)
                        }
                    ).padding(.horizontal)

                        .disabled(
                            uiState is ForgotPasswordEmailConfirmationState.Loading)

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
                isLoading: uiState is ForgotPasswordEmailConfirmationState.Loading)
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
