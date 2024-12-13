import SwiftUI

struct SplashView: View {
    @Binding var rootView : AppScreen
    @Environment(\.colorScheme) private var colorScheme
    @State private var navigationPath = NavigationPath() // Use NavigationPath for tracking navigation
    @State private var navigateToScreen: AppScreen? = nil // Optional type for conditional binding
    
    private let themeManager = ThemeManager.shared
    
    var body: some View {
        NavigationStack(path: $navigationPath) {
            ZStack {
                if let themeImage = themeManager.getThemeImage(isDarkMode: colorScheme == .dark) {
                    Image(uiImage: themeImage)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .padding(.horizontal, 48)
                }
            }
            .onAppear {
                rootView = .onboarding
    
                navigateAfterDelay()
            }
            .navigationDestination(for: AppScreen.self) { screen in
               
            }
        }
    }
    
    private func navigateAfterDelay() {
        DispatchQueue.main.asyncAfter(deadline: .now() + 3.0) {
            navigateToScreen = themeManager.determineInitialRoute() // Assign the optional result
            if let screen = navigateToScreen {
                navigationPath.append(screen) // Append only if the screen is non-nil
            }
        }
    }
}
