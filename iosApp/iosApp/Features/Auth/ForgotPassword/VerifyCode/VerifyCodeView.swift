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
        KoinHelper().getForgotPasswordVerifyCodeViewModel()
    @ObservedObject var emailViewModel: ForgotPasswordEmailConfirmationViewModel =
        KoinHelper().getForgotPasswordEmailConfirmationViewModel()

    @State private var uiState: ForgotPasswordVerifyCodeState = ForgotPasswordVerifyCodeState.Idle()
    @State private var emailUiState: ForgotPasswordEmailConfirmationState = ForgotPasswordEmailConfirmationState.Idle()
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

    let timerDuration = 60 // seconds

    // MARK: - Helper Methods
    private func handleVerificationState(_ state: ForgotPasswordVerifyCodeState) {
        switch state {
        case let success as ForgotPasswordVerifyCodeState.Success:
            isVerifySuccess = true
            otpId = success.data.otpId ?? ""
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

    private func handleSendOTPState(_ state: ForgotPasswordEmailConfirmationState) {
        switch state {
        case let success as ForgotPasswordEmailConfirmationState.Success:
            showSnackbar = true
            isSuccessSnackbar = true
            snackbarMessage = success.data.message ?? ""
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
        let params = ForgotPasswordVerifyOTPFormRequestDto(email: email, otpCode: otpCode)
        viewModel.verifyOTP(params: params)
    }

    private func handleAppear() {
        if !isSubscribed {
            isSubscribed = true
            viewModel.state.subscribe { state in
                if let state = state { self.uiState = state }
            }
            emailViewModel.state.subscribe { state in
                if let state = state { self.emailUiState = state }
            }
        }

        if isFirstLaunch {
            isFirstLaunch = false
            startTimer()
        } else if !isTimerStarted {
            startTimer()
        }
    }

    // MARK: - Subviews
    private func topSection() -> some View {
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
    }

    private func otpInputSection() -> some View {
        CustomOTPInput(otpValue: $otpCode)
            .padding(.horizontal)
    }

    private func timerResendSection() -> some View {
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
    }

    // MARK: - Body
    var body: some View {
        GeometryReader { geometry in
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    topSection()
                    otpInputSection()
                    timerResendSection()

                    NavigationLink(
                        destination: CreatePasswordView(rootView: $rootView, otpId: $otpId),
                        tag: true,
                        selection: $isVerifySuccess
                    ) {
                        EmptyView()
                    }

                    CustomOutlinedButton(
                        buttonText: NSLocalizedString("proceed", comment: ""),
                        buttonTextColor: .white,
                        buttonContainerColor: .colorCyan,
                        onClick: { verifyOTP() }
                    )
                    .disabled(uiState is ForgotPasswordVerifyCodeState.Loading)
                    .padding(.horizontal)

                    Spacer()
                }
                .frame(minHeight: geometry.size.height)
            }
            .scrollDisabled(true)
        }
        .onAppear { handleAppear() }
        .onChange(of: uiState) { handleVerificationState($0) }
        .onChange(of: emailUiState) { handleSendOTPState($0) }
        .modifier(
            CustomActivityIndicatorModifier(
                isLoading: uiState is ForgotPasswordVerifyCodeState.Loading
            )
        )
        .snackbar(
            show: $showSnackbar,
            bgColor: isSuccessSnackbar ? .green : .red,
            txtColor: .white,
            icon: "xmark",
            iconColor: .white,
            message: snackbarMessage
        )
    }
}
