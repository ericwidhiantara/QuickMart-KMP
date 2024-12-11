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
    var buttonTextColor: Color? = .primary
    var buttonTextFontSize: CGFloat = 14
    var height: CGFloat = 48
    var buttonContainerColor: Color = .blue
    var buttonBorderColor: Color = .gray
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
                    .foregroundColor(isButtonEnabled ? buttonTextColor : .gray)
                    .font(.system(size: buttonTextFontSize))
                if isWithIcon, let icon = buttonIcon {
                    Spacer().frame(width: 10)
                    icon
                        .renderingMode(.template)
                        .foregroundColor(isButtonEnabled ? buttonTextColor : .gray)
                }
            }
            .padding()
            .frame(maxWidth: .infinity, minHeight: height)
            .background(isButtonEnabled ? buttonContainerColor : Color.gray.opacity(0.3))
            .cornerRadius(10)
        }
        .disabled(!isButtonEnabled)
        .overlay(
            RoundedRectangle(cornerRadius: 10)
                .stroke(isButtonEnabled ? buttonBorderColor : Color.gray, lineWidth: 1)
        )
    }
}
