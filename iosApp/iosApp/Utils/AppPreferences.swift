//
//  Settings.swift
//  iosApp
//
//  Created by Eric on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import Foundation
import SwiftUI

class AppPreferences {
    // Singleton instance
    static let shared = AppPreferences()

    // Private initializer to ensure singleton pattern
    private init() {}

    // Keychain Service
    private struct KeychainKeys {
        static let accessToken = "com.app.accessToken"
        static let refreshToken = "com.app.refreshToken"
    }

    // UserDefaults keys for non-sensitive data
    private struct UserDefaultsKeys {
        static let appTheme = "APP_THEME"
        static let firstTime = "FIRST_TIME"
    }

    // Theme Management (using UserDefaults)
    func setTheme(_ theme: AppTheme) -> Bool {
        UserDefaults.standard.set(
            theme.rawValue, forKey: UserDefaultsKeys.appTheme)
        return getTheme() == theme
    }

    func getTheme() -> AppTheme {
        let themeString = UserDefaults.standard.string(
            forKey: UserDefaultsKeys.appTheme)
        return AppTheme(rawValue: themeString ?? AppTheme.default.rawValue)
            ?? .default
    }

    // First Time Flag (using UserDefaults)
    func setFirstTime(_ value: String = "1") -> String {
        UserDefaults.standard.set(value, forKey: UserDefaultsKeys.firstTime)
        return getFirstTime()
    }

    func getFirstTime() -> String {
        return UserDefaults.standard.string(forKey: UserDefaultsKeys.firstTime)
            ?? ""
    }

    // Secure Token Management (using Keychain)
    func setToken(_ value: String) -> Bool {
        return saveToKeychain(value, forKey: KeychainKeys.accessToken)
    }

    func getToken() -> String {
        return retrieveFromKeychain(forKey: KeychainKeys.accessToken) ?? ""
    }

    func setRefreshToken(_ value: String) -> Bool {
        return saveToKeychain(value, forKey: KeychainKeys.refreshToken)
    }

    func getRefreshToken() -> String {
        return retrieveFromKeychain(forKey: KeychainKeys.refreshToken) ?? ""
    }

    // Clear Tokens
    func clearToken() -> Bool {
        let accessTokenDeleted = deleteFromKeychain(
            forKey: KeychainKeys.accessToken)
        let refreshTokenDeleted = deleteFromKeychain(
            forKey: KeychainKeys.refreshToken)
        return accessTokenDeleted && refreshTokenDeleted
    }

    // Private Keychain Helper Methods
    private func saveToKeychain(_ value: String, forKey key: String) -> Bool {
        guard let data = value.data(using: .utf8) else { return false }

        // Delete existing item if it exists
        let deleteDictionary: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
        ]
        SecItemDelete(deleteDictionary as CFDictionary)

        // Create dictionary for new item
        let dictionary: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecValueData as String: data,
            kSecAttrAccessible as String: kSecAttrAccessibleWhenUnlocked,
        ]

        // Add new item
        let status = SecItemAdd(dictionary as CFDictionary, nil)
        return status == errSecSuccess
    }

    private func retrieveFromKeychain(forKey key: String) -> String? {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
            kSecReturnData as String: true,
            kSecMatchLimit as String: kSecMatchLimitOne,
        ]

        var result: AnyObject?
        let status = SecItemCopyMatching(query as CFDictionary, &result)

        guard status == errSecSuccess,
            let data = result as? Data,
            let value = String(data: data, encoding: .utf8)
        else {
            return nil
        }

        return value
    }

    private func deleteFromKeychain(forKey key: String) -> Bool {
        let query: [String: Any] = [
            kSecClass as String: kSecClassGenericPassword,
            kSecAttrAccount as String: key,
        ]

        let status = SecItemDelete(query as CFDictionary)
        return status == errSecSuccess || status == errSecItemNotFound
    }
}
