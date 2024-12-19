//
//  Settings.swift
//  iosApp
//
//  Created by Eric on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import Foundation
import SwiftUI
import Shared

class StringSettingConfig {
    private let settings: Settings
    private let key: String
    private let defaultValue: String
    
    init(settings: Settings, key: String, defaultValue: String) {
        self.settings = settings
        self.key = key
        self.defaultValue = defaultValue
    }
    
    func remove() {
        settings.remove(key: key)
    }
    
    func exists() -> Bool {
        return settings.hasKey(key: key)
    }
    
    func get() -> String {
        return settings.getString(key: key, defaultValue: defaultValue)
    }
    
    func set(_ value: String) -> Bool {
        do {
            settings.putString(key: key, value: value)
            return true
        } catch {
            return false
        }
    }
}

class AppPreferences {
    private var settings: Settings
    
    // Singleton instance
    static let shared = AppPreferences()
    
    // Private initializer to ensure singleton pattern
    private init() {
        // This is where you'd use NSUserDefaults settings
        settings = NSUserDefaultsSettings(delegate: UserDefaults.standard)
    }
    
    // Theme Management
    func setTheme(_ value: AppTheme) -> Bool {
        settings.putString(key: "app_theme", value: value.rawValue)
        return (settings.getString(key: "app_theme", defaultValue: AppTheme.default.rawValue)) == value.rawValue
    }
    
    func getTheme() -> AppTheme {
        settings.putString(key: "app_theme", value: AppTheme.default.rawValue)
        return AppTheme(rawValue: settings.getString(key: "app_theme", defaultValue: AppTheme.default.rawValue)) ?? .default
    }
    
    // First Time Flag
    func setFirstTime(_ value: String = "1") -> String {
        let config = StringSettingConfig(settings: settings, key: "first_time", defaultValue: "")
        _ = config.set(value)
        return config.get()
    }
    
    func getFirstTime() -> String {
        let config = StringSettingConfig(settings: settings, key: "first_time", defaultValue: "")
        return config.get()
    }
    
    // Access Token Management
    func setToken(_ value: String) -> String {
        let config = StringSettingConfig(settings: settings, key: "access_token", defaultValue: "")
        _ = config.set(value)
        return config.get()
    }
    
    func getToken() -> String {
        let config = StringSettingConfig(settings: settings, key: "access_token", defaultValue: "")
        return config.get()
    }
    
    // Refresh Token Management
    func setRefreshToken(_ value: String) -> String {
        let config = StringSettingConfig(settings: settings, key: "refresh_token", defaultValue: "")
        _ = config.set(value)
        return config.get()
    }
    
    func getRefreshToken() -> String {
        let config = StringSettingConfig(settings: settings, key: "refresh_token", defaultValue: "")
        return config.get()
    }
    
    // Clear Tokens
    func clearToken() -> Bool {
        let accessTokenConfig = StringSettingConfig(settings: settings, key: "access_token", defaultValue: "")
        let refreshTokenConfig = StringSettingConfig(settings: settings, key: "refresh_token", defaultValue: "")
        
        accessTokenConfig.remove()
        refreshTokenConfig.remove()
        
        return true
    }
}
