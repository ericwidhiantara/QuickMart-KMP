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
    case check_password
    case change_password
    case wishlist
    case cart
    case category
    case product
    case product_detail(String)              // productId
    case product_by_category(String, String) // categoryId, categoryName
    case search
    case order_list
    case order_detail(String)               // orderId

    func hash(into hasher: inout Hasher) {
        switch self {
        case .product_detail(let id):
            hasher.combine("product_detail"); hasher.combine(id)
        case .product_by_category(let id, let name):
            hasher.combine("product_by_category"); hasher.combine(id); hasher.combine(name)
        case .order_detail(let id):
            hasher.combine("order_detail"); hasher.combine(id)
        default:
            hasher.combine(String(describing: self))
        }
    }

    static func == (lhs: AppScreen, rhs: AppScreen) -> Bool {
        switch (lhs, rhs) {
        case (.product_detail(let a), .product_detail(let b)):
            return a == b
        case (.product_by_category(let a1, let a2), .product_by_category(let b1, let b2)):
            return a1 == b1 && a2 == b2
        case (.order_detail(let a), .order_detail(let b)):
            return a == b
        default:
            return String(describing: lhs) == String(describing: rhs)
        }
    }
}
