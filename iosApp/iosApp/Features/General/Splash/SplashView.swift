import SwiftUI

struct SplashView: View {
    @Binding var rootView: AppScreen
    @Environment(\.colorScheme) private var colorScheme

    private let themeManager = ThemeManager.shared

    var body: some View {
        ZStack {
            if let themeImage = themeManager.getThemeImage(
                isDarkMode: colorScheme == .dark
            ) {
                Image(uiImage: themeImage)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.horizontal, 48)
            }
        }
        .onAppear {
            navigateAfterDelay()
        }
    }

    private func navigateAfterDelay() {
        // Ensure the delay is accurately applied.
        DispatchQueue.main.asyncAfter(deadline: .now() + 2.0) {
            // Confirm rootView update with animation
            withAnimation {
                rootView = themeManager.determineInitialRoute()
            }
        }
    }
}
