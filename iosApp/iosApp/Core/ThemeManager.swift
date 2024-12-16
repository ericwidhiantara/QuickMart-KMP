import Foundation
import Shared  // Import the shared Kotlin module
import SwiftUI

class ThemeManager {
    static let shared = ThemeManager()

    // AppStorage properties for persistent user preferences
    @AppStorage("appTheme") private var storedTheme: String = "default"
    @AppStorage("isFirstTime") private var isFirstTime: String = ""
    @AppStorage("userToken") private var token: String = ""

    // Property to manage the current theme
    var currentTheme: AppTheme {
        get {
            switch storedTheme {
            case "light": return .light
            case "dark": return .dark
            default: return .default
            }
        }
        set {
            switch newValue {
            case .light: storedTheme = "light"
            case .dark: storedTheme = "dark"
            case .default: storedTheme = "default"
            }
        }
    }

    // Function to get the theme-specific image
    func getThemeImage(isDarkMode: Bool) -> UIImage? {
        // Use direct image names from the Xcode assets
        switch currentTheme {
        case .light:
            return UIImage(named: "SplashLight")  // Make sure the image is in Xcode assets
        case .dark:
            return UIImage(named: "SplashDark")
        case .default:
            return UIImage(named: isDarkMode ? "SplashLight" : "SplashDark")
        }
    }

    func getLogoImage(isDarkMode: Bool) -> UIImage? {
        // Use direct image names from the Xcode assets
        switch currentTheme {
        case .light:
            return UIImage(named: "LogoLight")  // Make sure the image is in Xcode assets
        case .dark:
            return UIImage(named: "LogoDark")
        case .default:
            return UIImage(named: isDarkMode ? "LogoLight" : "LogoDark")
        }
    }

    // Determine the initial route based on user preferences
    func determineInitialRoute() -> AppScreen {
        switch(isFirstTime){
        case "":
            return .onboarding
        case "1":
            if token.isEmpty {
                return .login
            } else {
                return .main
            }
        default:
            return .onboarding
        }
    
    }
}
