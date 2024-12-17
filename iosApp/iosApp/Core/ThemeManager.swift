import Foundation
import Shared  // Import the shared Kotlin module
import SwiftUI

class ThemeManager {
    static let shared = ThemeManager()

    // AppStorage properties for persistent user preferences
    private var storedTheme: AppTheme = AppPreferences.shared.getTheme()
    private var isFirstTime: String = AppPreferences.shared.getFirstTime()
    private var token: String = AppPreferences.shared.getToken()

    // Property to manage the current theme
    var currentTheme: AppTheme {
        switch storedTheme {
        case AppTheme.light: return .light
        case AppTheme.dark: return .dark
        default: return .default
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
        switch isFirstTime {
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
