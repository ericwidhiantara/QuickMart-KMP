//
//  AppTheme.swift
//  iosApp
//
//  Created by Eric on 10/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI

enum AppTheme: String, CaseIterable {
    case `default` = "Default"
    case dark = "Dark"
    case light = "Light"
}

// Theme View Modifier
struct AppThemeModifier: ViewModifier {
    @Environment(\.colorScheme) private var systemColorScheme
    let theme: AppTheme

    private var activeColorScheme: AppColorScheme {
        switch theme {
        case .light:
            return .light
        case .dark:
            return .dark
        case .default:
            return systemColorScheme == .dark ? .dark : .light
        }
    }

    func body(content: Content) -> some View {
        content
            .environment(\.appColorScheme, activeColorScheme)
            .preferredColorScheme(
                theme == .default ? nil : (theme == .dark ? .dark : .light))
    }
}

// Extension to make applying theme easier
extension View {
    func appTheme(_ theme: AppTheme) -> some View {
        self.modifier(AppThemeModifier(theme: theme))
    }
}
