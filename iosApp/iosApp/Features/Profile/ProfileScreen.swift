//
//  ProfileScreen.swift
//  iosApp
//
//  Created by Eric on 20/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct ProfileScreen: View {
    @State private var showLogoutDialog = false
    @ObservedObject var userViewModel: UserViewModel = KoinHelper()
        .getUserViewModel()

    @State var userState: UserState = UserState.Idle()

    let settingsSections: [SettingsSection] = [
        SettingsSection(
            title: "personal_information",
            items: [
                SettingsItem(
                    title: "shipping_address", icon: "Shipping",
                    action: {
                        // Navigate to Shipping Address screen
                    }),
                SettingsItem(
                    title: "order_history", icon: "OrderHistory",
                    action: {
                        // Navigate to Order History screen
                    }),
            ]
        ),
        SettingsSection(
            title: "support_and_information",
            items: [
                SettingsItem(
                    title: "privacy_policy", icon: "PrivacyPolicy",
                    action: {
                        // Navigate to Privacy Policy screen
                    }),
                SettingsItem(
                    title: "terms_and_conditions", icon: "TermsAndCondition",
                    action: {
                        // Navigate to Terms and Conditions screen
                    }),
                SettingsItem(
                    title: "faqs", icon: "Faq",
                    action: {
                        // Navigate to FAQs screen
                    }),
            ]
        ),
        SettingsSection(
            title: "account_management",
            items: [
                SettingsItem(
                    title: "change_password", icon: "ChangePassword",
                    action: {
                        // Navigate to Change Password screen
                    }
                ),
                SettingsItem(
                    title: "theme", icon: "DarkTheme",
                    action: {
                        // Navigate to Theme settings
                    }),
                SettingsItem(
                    title: "language", icon: "Language",
                    action: {
                        // Navigate to Language settings
                    }),
            ]
        ),
    ]

    var body: some View {

        let appUiState = userViewModel.userState

        NavigationView {
            VStack {
                ProfileTopBar(
                    userState: userState,
                    onLogoutClick: {
                        showLogoutDialog = true
                    }
                )
                .background(Color.colorCyan)
                List {
                    ForEach(settingsSections, id: \.title) { section in
                        Section(
                            header: Text(LocalizedStringKey(section.title))
                                .font(.headline)
                                .foregroundColor(.primary)
                        ) {
                            ForEach(section.items, id: \.title) { item in
                                Button(action: item.action) {
                                    HStack {
                                        Image(item.icon)
                                            .resizable()
                                            .frame(width: 24, height: 24)
                                        Text(LocalizedStringKey(item.title))
                                            .foregroundColor(.primary)
                                        Spacer()
                                        Image(systemName: "chevron.forward")
                                            .foregroundColor(.secondary)
                                    }
                                    .padding(.vertical, 8)
                                }
                            }
                        }.textCase(nil)

                    }
                }
                .listStyle(GroupedListStyle())
            }.onAppear {
                appUiState.subscribe { state in
                    self.userState = state!
                }
                userViewModel.getUserLogin()
            }
            .confirmationDialog(
                LocalizedStringKey("confirm_logout"),
                isPresented: $showLogoutDialog, titleVisibility: .visible
            ) {
                Button(LocalizedStringKey("logout"), role: .destructive) {
                    // Handle logout logic
                }
                Button("Cancel", role: .cancel) {}
            }
            .navigationBarHidden(true)
        }
    }
}

// MARK: - Supporting Models

struct SettingsSection {
    let title: String
    let items: [SettingsItem]
}

struct SettingsItem {
    let title: String
    let icon: String
    let action: () -> Void
}

// MARK: - ProfileTopBar
struct ProfileTopBar: View {
    let userState: UserState
    let onLogoutClick: () -> Void

    var body: some View {
        switch userState {
        case let data as UserState.Success:
            ProfileTopBarContent(
                fullName: data.data.fullname ?? "",
                email: data.data.email ?? "",
                image: data.data.profilePicture == ""
                    ? "https://avatar.iran.liara.run/public/66"
                    : data.data.profilePicture!,
                onLogoutClick: onLogoutClick,
                isLoading: false
            )
        case is UserState.Loading:
            HStack {
                Spacer()
                ProgressView().frame(width: 24, height: 24)
                    .progressViewStyle(
                        CircularProgressViewStyle(tint: .white)
                    )
                Spacer()
                Button(action: onLogoutClick) {
                    Image("Logout")

                }
            }.padding()
        default:
            ProfileTopBarContent(
                fullName: "Guest",
                email: "guest@example.com",
                image: "https://avatar.iran.liara.run/public/66",
                onLogoutClick: onLogoutClick,
                isLoading: false
            )
        }
    }
}

struct ProfileTopBarContent: View {
    let fullName: String
    let email: String
    let image: String
    let onLogoutClick: () -> Void
    let isLoading: Bool

    var body: some View {
        HStack {

            AsyncImage(
                url: URL(string: image),
                content: { image in
                    image
                        .resizable()
                        .frame(width: 48, height: 48)
                        .clipShape(Circle())
                        .padding()
                },
                placeholder: {
                    Circle()
                        .fill(Color.gray.opacity(0.3))
                        .frame(width: 48, height: 48)
                        .overlay(
                            ProgressView()
                                .frame(width: 24, height: 24)
                        )
                }
            )
            VStack(alignment: .leading) {
                Text(isLoading ? "" : fullName)
                    .font(.system(size: 14))
                    .foregroundColor(.colorWhite)
                Text(isLoading ? "" : email)
                    .font(.subheadline)
                    .foregroundColor(.colorWhite)
            }

            Spacer()
            Button(action: onLogoutClick) {
                Image("Logout")

            }
        }
        .padding()
    }
}
