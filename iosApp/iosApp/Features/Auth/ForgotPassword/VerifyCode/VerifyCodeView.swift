//
//  VerifyCodeView.swift
//  iosApp
//
//  Created by Eric on 19/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct VerifyCodeView: View {
    @Binding var rootView: AppScreen
    @Binding var email: String

    @ObservedObject var viewModel: ForgotPasswordVerifyCodeViewModel =
        KoinHelper()
        .getForgotPasswordVerifyCodeViewModel()
    @ObservedObject var emailViewModel:
        ForgotPasswordEmailConfirmationViewModel =
            KoinHelper()
            .getForgotPasswordEmailConfirmationViewModel()

    @State private var uiState: ForgotPasswordVerifyCodeState =
        ForgotPasswordVerifyCodeState.Idle()
    @State private var emailUiState: ForgotPasswordEmailConfirmationState =
        ForgotPasswordEmailConfirmationState.Idle()
    @State private var otpCode = ""
    @State private var remainingTime: Int = 60
    @State private var isTimerFinished = false
    @State private var showLoadingDialog = false
    @State private var showErrorDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var isSuccessSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isSubscribed: Bool = false
    @State private var isTimerStarted: Bool = false
    @State private var isFirstLaunch: Bool = true
    @State private var otpId: String = ""
    @State private var isVerifySuccess: Bool? = false

    let timerDuration = 60  // seconds

    private func handleVerificationState(
        _ state: ForgotPasswordVerifyCodeState
    ) {
        switch state {
        case let success as ForgotPasswordVerifyCodeState.Success:
            isVerifySuccess = true
            
            // ToDO: pass otpId to creaatePassword screen
            otpId = success.data.otpId!
            break

        case let errorState as ForgotPasswordVerifyCodeState.Error:
            showSnackbar = true
            isVerifySuccess = false
            isSuccessSnackbar = false
            snackbarMessage = errorState.message
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                showSnackbar = false
            }
        default:
            showSnackbar = false
        }
    }

    private func handleSendOTPState(
        _ state: ForgotPasswordEmailConfirmationState
    ) {
        switch state {
        case let success as ForgotPasswordEmailConfirmationState.Success:
            showSnackbar = true
            isSuccessSnackbar = true
            snackbarMessage = success.data.message ?? ""

            break

        case let errorState as ForgotPasswordEmailConfirmationState.Error:
            showSnackbar = true
            isSuccessSnackbar = false
            snackbarMessage = errorState.message
            DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                showSnackbar = false
            }
        default:
            showSnackbar = false
        }
    }

    private var formattedTime: String {
        let minutes = remainingTime / 60
        let seconds = remainingTime % 60
        return String(format: "%02d:%02d", minutes, seconds)
    }

    private func startTimer() {
        remainingTime = timerDuration
        isTimerStarted = true
        isTimerFinished = false

        Timer.scheduledTimer(withTimeInterval: 1.0, repeats: true) { timer in
            if remainingTime > 0 {
                remainingTime -= 1
            } else {
                isTimerFinished = true
                timer.invalidate()
                isTimerStarted = false
            }
        }
    }

    private func resendOTP(email: String) {
        let params = ForgotPasswordSendOTPFormRequestDto(email: email)

        emailViewModel.sendOTP(params: params)
        startTimer()
    }

    private func verifyOTP() {
        let params = ForgotPasswordVerifyOTPFormRequestDto(
            email: email,
            otpCode: otpCode
        )

        viewModel.verifyOTP(params: params)
    }

    var body: some View {
        GeometryReader { geometry in
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    // Top Section with Titles
                    VStack(alignment: .leading, spacing: 8) {
                        Text("email_verification")
                            .font(.title)
                            .fontWeight(.bold)

                        Text("enter_verification_code")
                            .font(.subheadline)
                            .foregroundColor(.secondary)
                    }
                    .padding(.top, 20)
                    .padding(.horizontal)

                    // OTP Input
                    CustomOTPInput(otpValue: $otpCode)
                        .padding(.horizontal)

                    // Timer/Resend Section
                    HStack {
                        if isTimerFinished {
                            Button("resend_code") {
                                resendOTP(email: email)
                            }
                            .foregroundColor(.colorCyan)
                        } else {
                            Text("resend_code_timer")
                                .foregroundColor(.gray)
                            Text(formattedTime)
                                .foregroundColor(.gray)
                        }
                    }
                    .frame(maxWidth: .infinity, alignment: .center)
                    .padding(.horizontal)

                    NavigationLink(
                        destination: VerifyCodeView(rootView: $rootView, email: $email),
                        tag: true,
                        selection: $isVerifySuccess
                    ) {
                        EmptyView()
                    }
                    CustomOutlinedButton(
                        buttonText: NSLocalizedString(
                            "proceed",
                            comment: ""
                        ),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: {
                            verifyOTP()
                        }

                    )
                    .disabled(
                        uiState is ForgotPasswordVerifyCodeState.Loading
                    )
                    .padding(.horizontal)

                    Spacer()
                }
                .frame(minHeight: geometry.size.height)

            }

            .scrollDisabled(true)
        }
        .onAppear {
            // Ensure state subscription happens only once
            if !isSubscribed {
                isSubscribed = true
                viewModel.state.subscribe { state in
                    if let state = state {
                        self.uiState = state
                    }
                }
                emailViewModel.state.subscribe { state in
                    if let state = state {
                        self.emailUiState = state
                    }
                }
            }

            // Handle first launch
            if isFirstLaunch {
                isFirstLaunch = false
                startTimer()
            } else if !isTimerStarted {
                startTimer()
            }
        }
        .onChange(of: uiState) { state in
            handleVerificationState(state)
        }
        .onChange(of: emailUiState) { state in
            handleSendOTPState(state)
        }
        .modifier(
            CustomActivityIndicatorModifier(
                isLoading: uiState
                    is ForgotPasswordVerifyCodeState.Loading)
        )
        .snackbar(
            show: $showSnackbar, bgColor: isSuccessSnackbar ? .green : .red,
            txtColor: .white,
            icon: "xmark", iconColor: .white,
            message: snackbarMessage
        )
    }
}
