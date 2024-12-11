//
//  AppColorScheme.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

// Custom Color Scheme struct
struct AppColorScheme {
    let primary: Color
    let onPrimary: Color
    let primaryContainer: Color
    let onPrimaryContainer: Color
    
    let secondary: Color
    let onSecondary: Color
    let secondaryContainer: Color
    let onSecondaryContainer: Color
    
    let background: Color
    let onBackground: Color
    
    let surface: Color
    let onSurface: Color
    let surfaceVariant: Color
    let onSurfaceVariant: Color
    
    let error: Color
    let onError: Color
    
    // Static methods to create color schemes
    static var light: AppColorScheme {
        AppColorScheme(
            primary: .colorCyan,
            onPrimary: .colorWhite,
            primaryContainer: .colorCyan50,
            onPrimaryContainer: .colorCyan50Dark,
            
            secondary: .colorGrey100,
            onSecondary: .colorBlack,
            secondaryContainer: .colorGrey50,
            onSecondaryContainer: .colorGrey150,
            
            background: .colorBackground,
            onBackground: .colorBlack,
            
            surface: .colorWhite,
            onSurface: .colorBlack,
            surfaceVariant: .colorGrey50,
            onSurfaceVariant: .colorGrey150,
            
            error: .colorRed,
            onError: .colorWhite
        )
    }
    
    static var dark: AppColorScheme {
        AppColorScheme(
            primary: .colorCyan,
            onPrimary: .colorWhite,
            primaryContainer: .colorCyan50Dark,
            onPrimaryContainer: .colorCyan50Dark,
            
            secondary: .colorGrey150Dark,
            onSecondary: .colorWhite,
            secondaryContainer: .colorGrey50Dark,
            onSecondaryContainer: .colorGrey150Dark,
            
            background: .colorBackgroundDark,
            onBackground: .colorWhite,
            
            surface: .colorGrey50Dark,
            onSurface: .colorWhite,
            surfaceVariant: .colorGrey150Dark,
            onSurfaceVariant: .colorGrey150,
            
            error: .colorRed,
            onError: .colorWhite
        )
    }
}

// Environment Key for Custom Color Scheme
private struct AppColorSchemeKey: EnvironmentKey {
    static let defaultValue = AppColorScheme.light
}

extension EnvironmentValues {    var appColorScheme: AppColorScheme {
        get { self[AppColorSchemeKey.self] }
        set { self[AppColorSchemeKey.self] = newValue }
    }
}
