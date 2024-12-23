//
//  CheckPasswordView.swift
//  iosApp
//
//  Created by Eric on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct CheckPasswordView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: CheckPasswordViewModel =
        KoinHelper()
        .getCheckPasswordViewModel()

    @State var uiState: CheckPasswordState =
        CheckPasswordState.Idle()

    @State private var password: String = "!_Ash008"
    @State private var isPasswordVisible: Bool = false

    @State private var shouldValidate: Bool = false
    @State private var showErrorDialog: Bool = false
    @State private var showLoadingDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isConfirmSuccess: Bool? = false
    @State private var isSuccessSnackbar: Bool = false

    @Environment(\.colorScheme) var colorScheme

    private func handleState(_ state: CheckPasswordState) {
        switch state {
        case is CheckPasswordState.Success:

            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                self.isConfirmSuccess = true
            }
            break
        case let errorState as CheckPasswordState.Error:
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
                        Text("old_password")
                            .font(.title)
                            .fontWeight(.bold)

                        Text("old_password_desc")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .padding(.top, 20)
                    .padding(.horizontal)
                    NavigationLink(
                        destination: ChangePasswordView(rootView: $rootView),
                        tag: true,
                        selection: $isConfirmSuccess
                    ) {
                        EmptyView()
                    }
                    CustomTextField(
                        type: .password,
                        titleLabel: NSLocalizedString(
                            "old_password", comment: ""),
                        value: $password,
                        validator: { $0.count >= 8 },
                        shouldValidate: shouldValidate,
                        placeholder: NSLocalizedString(
                            "password_placeholder", comment: ""),
                        textInputAutocapitalization: .never
                    ).padding(.horizontal)

                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "continue_text",
                            comment: ""
                        ),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {

                            guard
                                !password.isEmpty
                            else {
                                shouldValidate = true
                                return
                            }

                            let params =
                                CheckPasswordFormRequestDto(
                                    password: password
                                )

                            appUiState.subscribe { state in
                                self.uiState = state!
                            }
                            viewModel.checkPassword(params: params)
                        }
                    ).padding(.horizontal)

                        .disabled(
                            uiState is CheckPasswordState.Loading)

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
                isLoading: uiState is CheckPasswordState.Loading)
        )
        .snackbar(
            show: $showSnackbar, bgColor: isSuccessSnackbar ? .green : .red,
            txtColor: .white,
            icon: "xmark", iconColor: .white,
            message: snackbarMessage
        )
    }
}
