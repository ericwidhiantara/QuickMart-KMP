//
//  AppScreen.swift
//  iosApp
//
//  Created by Eric on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation

enum AppScreen: Hashable {
    case splash
    case onboarding
    case login
    case register
    case verify_email
    case forgot_password_confirm_email
    case forgot_password_verify_code
    case create_password
    case password_created
    case main
}
