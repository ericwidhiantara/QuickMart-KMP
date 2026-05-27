//
//  CustomOutlinedButton.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomOutlinedButton: View {
    let buttonText: String
    var buttonTextColor: Color = .primary
    var disabledTextColor: Color = .gray
    var buttonTextFontSize: CGFloat = 14
    var height: CGFloat = 48
    var buttonContainerColor: Color = .blue
    var disabledContainerColor: Color = .gray.opacity(0.3)
    var buttonBorderColor: Color = .gray
    var disabledBorderColor: Color = .gray
    var onClick: () -> Void
    var isWithIcon: Bool = false
    var buttonIcon: Image? = nil
    var isButtonEnabled: Bool = true

    var body: some View {
        Button(action: {
            if isButtonEnabled {
                onClick()
            }
        }) {
            HStack {
                Text(buttonText)
                    .foregroundColor(isButtonEnabled ? buttonTextColor : disabledTextColor)
                    .font(.system(size: buttonTextFontSize))
                if isWithIcon, let icon = buttonIcon {
                    Spacer().frame(width: 10)
                    icon
                        .renderingMode(.template)
                        .foregroundColor(isButtonEnabled ? buttonTextColor : disabledTextColor)
                }
            }
            .padding()
            .frame(maxWidth: .infinity, minHeight: height)
            .glassButton(
                tint: buttonContainerColor,
                cornerRadius: 10,
                isEnabled: isButtonEnabled,
                fallback: buttonContainerColor,
                fallbackDisabled: disabledContainerColor
            )
        }
        .disabled(!isButtonEnabled)
        // Border only on older iOS — glass provides visual boundary on iOS 26+
        .overlay(
            Group {
                if #available(iOS 26, *) {
                    EmptyView()
                } else {
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(
                            isButtonEnabled ? buttonBorderColor : disabledBorderColor,
                            lineWidth: 1
                        )
                }
            }
        )
    }
}
