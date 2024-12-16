//
//  Extensions.swift
//  iosApp
//
//  Created by Eric on 13/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI

extension View {
    func snackbar(
        show: Binding<Bool>, bgColor: Color, txtColor: Color, icon: String?,
        iconColor: Color, message: String
    ) -> some View {
        self.modifier(
            SnackbarModifier(
                show: show, bgColor: bgColor, txtColor: txtColor, icon: icon,
                iconColor: iconColor, message: message))
    }
}
