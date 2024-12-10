import SwiftUI

struct SplashView: View {
    @Environment(\.colorScheme) private var colorScheme
    @State private var navigateToScreen: AppScreen?
    
    private let themeManager = ThemeManager.shared
    
    var body: some View {
        ZStack {
            // Use Image(uiImage:) to load a UIImage
            if let themeImage = themeManager.getThemeImage(isDarkMode: colorScheme == .dark) {
                Image(uiImage: themeImage) // This works with a UIImage
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.horizontal, 48)
            }
        }
        .onAppear {
            navigateAfterDelay()
        }
        .onChange(of: navigateToScreen) { newValue in
            handleNavigation(to: newValue)
        }
    }
    
    private func navigateAfterDelay() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
            navigateToScreen = themeManager.determineInitialRoute()
        }
    }
    
    private func handleNavigation(to screen: AppScreen?) {
        guard let screen = screen else { return }
        
        switch screen {
        case .onboarding:
            // Navigate to onboarding
            print("Navigating to Onboarding")
        case .login:
            // Navigate to login
            print("Navigating to Login")
        case .main:
            // Navigate to main screen
            print("Navigating to Main Screen")
        case .splash:
            break
        }
    }
}
