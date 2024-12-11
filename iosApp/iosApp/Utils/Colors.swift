//
//  Colors.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

// Color extensions to match the Android colors
extension Color {
    // Grays
    static let colorGrey50 = Color(hex: 0xFFF4F5FD)
    static let colorGrey50Dark = Color(hex: 0xFF1C1B1B)
    static let colorGrey100 = Color(hex: 0xFFC0C0C0)
    static let colorGrey150 = Color(hex: 0xFF6F7384)
    static let colorGrey150Dark = Color(hex: 0xFFA2A2A6)
    
    // Cyan shades
    static let colorCyan = Color(hex: 0xFF21D4B4)
    static let colorCyan50 = Color(hex: 0xFFF4FDFA)
    static let colorCyan50Dark = Color(hex: 0xFF212322)
    
    // Background colors
    static let colorBackground = Color(hex: 0xFFFFFFFF)
    static let colorBackgroundDark = Color(hex: 0xFF1C1B1B)
    
    // Other colors
    static let colorRed = Color(hex: 0xFFEE4D4D)
    static let colorPurple = Color(hex: 0xFF4F1FDA)
    static let colorBlack = Color(hex: 0xFF1C1B1B)
    static let colorWhite = Color(hex: 0xFFFFFFFF)
    
    // Border colors
    static let borderColorDark = Color(hex: 0xFF282828)
    static let borderColorLight = Color(hex: 0xFFF4F5FD)
    
    // Hex initializer
    init(hex: UInt, alpha: Double = 1.0) {
        self.init(
            .sRGB,
            red: Double((hex >> 16) & 0xff) / 255,
            green: Double((hex >> 08) & 0xff) / 255,
            blue: Double((hex >> 00) & 0xff) / 255,
            opacity: alpha
        )
    }
}
