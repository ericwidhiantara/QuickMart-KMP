//
//  CartSummaryBar.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct CartSummaryBar: View {
    let shippingCost: Double
    let subtotal: Double
    let onCheckout: () -> Void

    var body: some View {
        VStack {
            OrderInfoRow(label: "subtotal", amount: subtotal)
            OrderInfoRow(label: "shipping_cost", amount: shippingCost)
            TotalOrderInfoRow(label: "total", amount: subtotal + shippingCost)
            Button(action: onCheckout) {
                Text("checkout")
                    .padding()
                    .frame(maxWidth: .infinity)
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
        }
        .padding()
    }
}

struct OrderInfoRow: View {
    let label: String
    let amount: Double

    var body: some View {
        HStack {
            Text(label)
                .font(.system(size: 12))
            Spacer()
            Text(String(format: "$%.2f", amount))
                .font(.system(size: 12))
        }
    }
}

struct TotalOrderInfoRow: View {
    let label: String
    let amount: Double

    var body: some View {
        HStack {
            Text(label)
                .font(.system(size: 16, weight: .medium))
            Spacer()
            Text(String(format: "$%.2f", amount))
                .font(.system(size: 16, weight: .medium))
        }
    }
}
