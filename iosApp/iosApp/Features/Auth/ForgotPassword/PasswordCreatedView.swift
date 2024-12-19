//
//  PasswordCreated.swift
//  iosApp
//
//  Created by Eric on 19/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct PasswordCreatedView: View {
    @Binding var rootView: AppScreen
    private let themeManager = ThemeManager.shared

    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.appColorScheme) private var theme

    var body: some View {
        VStack(spacing: 20) {
            ZStack {
                theme.primaryContainer
                    .cornerRadius(32)
                    .frame(height: UIScreen.main.bounds.height * 0.52)
                    .overlay(
                        Image("Lock").resizable().frame(width: 245, height: 301)
                    )
            }.padding()

            Text("change_password_success").font(.system(size: 24)).fontWeight(
                .bold
            ).multilineTextAlignment(.center)

            Text("change_password_success_desc")
                .multilineTextAlignment(.center)
            CustomOutlinedButton(
                buttonText: NSLocalizedString(
                    "login",
                    comment: ""
                ),
                buttonTextColor: .white,
                buttonContainerColor: .colorCyan,
                onClick: {
                    rootView = .login

                }
            ).padding(.horizontal)

        }
    }
}
