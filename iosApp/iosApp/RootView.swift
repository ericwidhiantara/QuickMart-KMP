//
//  RootView.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//



import SwiftUI

struct ContentView : View {
    @State private var rootView : AppScreen = .splash
    
    var body: some View {
        NavigationView {
            switch rootView {
            case .splash:
                SplashView(rootView: $rootView)
            case .onboarding:
                OnboardingScreen(rootView: $rootView)
            }
        }.navigationViewStyle(StackNavigationViewStyle()).appTheme(.default) // or .light, .dark
    }
}
