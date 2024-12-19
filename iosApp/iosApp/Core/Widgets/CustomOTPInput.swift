//
//  CustomOTPInput.swift
//  iosApp
//
//  Created by Eric on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomOTPInput: View {
    let otpLength: Int
    @Binding var otpValue: String
    @FocusState private var focusedField: Int?

    init(otpLength: Int = 6, otpValue: Binding<String>) {
        self.otpLength = otpLength
        self._otpValue = otpValue
        if otpValue.wrappedValue.count > otpLength {
            self.otpValue = String(otpValue.wrappedValue.prefix(otpLength))
        }
    }

    var body: some View {
        HStack(spacing: 10) {
            ForEach(0..<otpLength, id: \.self) { index in
                SingleOTPTextField(
                    text: Binding(
                        get: {
                            if index < otpValue.count {
                                let charIndex = otpValue.index(
                                    otpValue.startIndex, offsetBy: index)
                                return String(otpValue[charIndex])
                            }
                            return ""
                        },
                        set: { newValue in
                            updateOTPValue(newValue, at: index)
                        }
                    ),
                    isFocused: Binding(
                        get: { focusedField == index },
                        set: { isFocused in
                            if isFocused {
                                focusedField = index
                            }
                        }
                    )
                )
                .focused($focusedField, equals: index)
            }
        }
        .onAppear {
            if otpValue.count < otpLength {
                otpValue = String(repeating: " ", count: otpLength)
            }
        }
    }

    private func updateOTPValue(_ newValue: String, at index: Int) {
        guard !newValue.isEmpty else {
            // Handle deletion
            if index >= 0 {
                // Clear current field
                let range =
                    otpValue.index(
                        otpValue.startIndex, offsetBy: index)...otpValue.index(
                        otpValue.startIndex, offsetBy: index)
                otpValue.replaceSubrange(range, with: " ")

                // Move focus to previous field
                if index > 0 {
                    focusedField = index - 1

                    // Also clear the previous field when backspacing
                    let prevRange =
                        otpValue.index(
                            otpValue.startIndex, offsetBy: index - 1)...otpValue
                        .index(otpValue.startIndex, offsetBy: index - 1)
                    otpValue.replaceSubrange(prevRange, with: " ")
                }
            }
            return
        }

        // Ensure only numeric input
        let filteredValue = newValue.filter { $0.isNumber }
        guard !filteredValue.isEmpty else { return }

        // Take only the first digit if we have multiple
        let singleDigit = String(filteredValue.prefix(1))

        // Update the current field
        let range =
            otpValue.index(
                otpValue.startIndex, offsetBy: index)...otpValue.index(
                otpValue.startIndex, offsetBy: index)
        otpValue.replaceSubrange(range, with: singleDigit)

        // Move to next field if not last
        if index < otpLength - 1 {
            focusedField = index + 1
        }

        // Ensure total length does not exceed otpLength
        if otpValue.count > otpLength {
            otpValue = String(otpValue.prefix(otpLength))
        }
    }
}

struct SingleOTPTextField: View {
    @Binding var text: String
    @Binding var isFocused: Bool

    var body: some View {
        TextField("", text: $text)
            .keyboardType(.numberPad)
            .multilineTextAlignment(.center)
            .frame(width: 48, height: 48)
            .background(Color.gray.opacity(0.1))
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(
                        isFocused ? .colorCyan : Color.gray.opacity(0.5),
                        lineWidth: 1)
            )
            .onChange(of: text) { newValue in
                // Limit to single character
                if newValue.count > 1 {
                    text = String(newValue.prefix(1))
                }
            }
    }
}
