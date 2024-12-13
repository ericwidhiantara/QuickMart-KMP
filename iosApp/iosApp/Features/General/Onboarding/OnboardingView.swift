import SwiftUI

struct OnboardingView: View {
    @Binding var rootView : AppScreen
    @State private var currentIndex: Int = 0
    
    let titles = [
        NSLocalizedString("onboarding_title_1", comment: "Title 1"),
        NSLocalizedString("onboarding_title_2", comment: "Title 2"),
        NSLocalizedString("onboarding_title_3", comment: "Title 3"),
    ]
    let subtitles = [
        NSLocalizedString("onboarding_subtitle_1", comment: "Subtitle 1"),
        NSLocalizedString("onboarding_subtitle_2", comment: "Subtitle 2"),
        NSLocalizedString("onboarding_subtitle_3", comment: "Subtitle 3")
    ]
    let images = [
        "Onboarding1",
        "Onboarding2",
        "Onboarding3"
    ]
    
    @Environment(\.colorScheme) private var colorScheme
    @Environment(\.appColorScheme) private var theme
    
    private let themeManager = ThemeManager.shared
    
    private let screenWidth: CGFloat = UIScreen.main.bounds.width // Screen width
    
    
    var body: some View {
        NavigationStack {
            VStack {
                ZStack {
                    theme.primaryContainer
                        .cornerRadius(32)
                        .frame(height: UIScreen.main.bounds.height * 0.52)
                        .overlay(
                            VStack {
                                HStack {
                                    if currentIndex > 0 {
                                        Button(action: {
                                            currentIndex -= 1
                                        }) {
                                            Image("ArrowBack")
                                                .renderingMode(.template)
                                                .foregroundColor(.primary)
                                                .frame(width: 32, height: 32)
                                        }
                                    } else {
                                        
                                        if let themeImage = themeManager.getLogoImage(isDarkMode: colorScheme == .dark) {
                                            Image(uiImage: themeImage)
                                                .resizable()
                                                .frame(width: 104, height: 32)
                                        }
                                        
                                    }
                                    
                                    Spacer()
                                    
                                    if currentIndex < titles.count - 1 {
                                        Button(action: {
                                            // Implement Skip action
                                        }) {
                                            Text( NSLocalizedString("skip_for_now", comment: "Skip for now"))
                                                .foregroundColor(.colorCyan)
                                        }
                                    }
                                }
                                .padding()
                                
                                Spacer()
                                
                                Image(images[currentIndex])
                                    .resizable()
                                    .frame(width: 240, height: 240)
                                
                                Spacer()
                            }
                                .padding()
                        )
                }
                
                Spacer().frame(height: 24)
                
                Text(titles[currentIndex])
                    .font(.system(size: 24))
                    .fontWeight(.bold)
                    .multilineTextAlignment(.center)
                
                Spacer().frame(height: 16)
                
                Text(subtitles[currentIndex])
                    .font(.system(size: 14))
                    .multilineTextAlignment(.center)
                
                Spacer().frame(height: 16)
                
                if currentIndex == titles.count - 1 {
                    HStack {
                        CustomOutlinedButton(
                            buttonText:  NSLocalizedString("login", comment: "Login"),
                            buttonTextColor: colorScheme == .dark ? theme.onPrimary : theme.onPrimaryContainer,
                            buttonContainerColor: colorScheme == .dark ? theme.onPrimaryContainer : .clear,
                            buttonBorderColor: theme.onPrimaryContainer,
                            onClick: {
                                rootView = .login
                            },
                            isWithIcon: false
                        )
                        .frame(width: screenWidth * 0.45)
                        
                        CustomOutlinedButton(
                            buttonText: NSLocalizedString("get_started", comment: "Get started"),
                            buttonTextColor: .white,
                            buttonContainerColor: colorScheme == .dark ? theme.primary : theme.onPrimaryContainer,
                            onClick: {
                                
                            },
                            isWithIcon: true,
                            buttonIcon: Image("ArrowForward")
                        )
                        .frame(width: screenWidth * 0.45)
                    }
                } else {
                    CustomOutlinedButton(
                        buttonText:  NSLocalizedString("next", comment: "Next"),
                        buttonTextColor: theme.onPrimary,
                        buttonContainerColor: colorScheme == .dark ? theme.primary : theme.onPrimaryContainer,
                        onClick: {
                            currentIndex += 1
                        }
                    )
                }
                
                Spacer().frame(height: 24)
                
                HStack(spacing: 5) {
                    ForEach(0..<titles.count, id: \.self) { index in
                        Circle()
                            .fill(index == currentIndex ? .colorCyan : theme.onBackground)
                            .frame(width: 10, height: 10)
                    }
                }
                .frame(height: 26)
            }
            .padding()
        }
    }
}
