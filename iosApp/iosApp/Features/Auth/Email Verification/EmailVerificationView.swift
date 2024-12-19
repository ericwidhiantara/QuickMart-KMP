import Shared
//
//  EmailVerification.swift
//  iosApp
//
//  Created by Eric on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI

struct EmailVerificationView: View {
    @Binding var rootView: AppScreen

    @ObservedObject var viewModel: EmailVerificationViewModel = KoinHelper()
        .getEmailVerificationViewModel()

    @State private var uiState: EmailVerificationState =
        EmailVerificationState.Idle()
    @State private var otpCode = ""
    @State private var remainingTime: Int = 60
    @State private var isTimerFinished = false
    @State private var showLoadingDialog = false
    @State private var showErrorDialog: Bool = false
    @State private var showSnackbar: Bool = false
    @State private var isSuccessSnackbar: Bool = false
    @State private var snackbarMessage: String = ""
    @State private var isSubscribed = false
    @State private var isTimerStarted = false
    @State private var isFirstLaunch = true

    let timerDuration = 60  // seconds

    private func handleVerificationState(_ state: EmailVerificationState) {
        switch state {
        case let success as EmailVerificationState.Success:
            showSnackbar = true
            isSuccessSnackbar = true
            snackbarMessage = success.data.message ?? ""

            break

        case let success as EmailVerificationState.VerifySuccess:
            // Navigate to main screen on successful verification
            showSnackbar = true
            isSuccessSnackbar = true
            snackbarMessage = success.data.message ?? ""
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
                rootView = .main
            }

            break
        case let errorState as EmailVerificationState.Error:
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

    private func startTimer(sendInitialOTP: Bool) {
        remainingTime = timerDuration
        isTimerStarted = true
        isTimerFinished = false

        if sendInitialOTP {
            viewModel.sendOTP()  // Send OTP only if it's the first launch or explicitly required
        }

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

    private func resendOTP() {
        viewModel.sendOTP()
        startTimer(sendInitialOTP: true)
    }

    private func verifyOTP() {
        let params = VerifyOTPFormRequestDto(
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
                                resendOTP()
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
                    .disabled(uiState is EmailVerificationState.Loading)
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
            }

            // Handle first launch
            if isFirstLaunch {
                isFirstLaunch = false
                startTimer(sendInitialOTP: true)  // Send OTP initially
            } else if !isTimerStarted {
                startTimer(sendInitialOTP: false)  // No OTP resend for subsequent appearances
            }
        }
        .onChange(of: uiState) { state in
            handleVerificationState(state)
        }
        .modifier(
            CustomActivityIndicatorModifier(
                isLoading: uiState is EmailVerificationState.Loading)
        )
        .snackbar(
            show: $showSnackbar, bgColor: isSuccessSnackbar ? .green : .red,
            txtColor: .white,
            icon: "xmark", iconColor: .white,
            message: snackbarMessage
        )
    }
}
