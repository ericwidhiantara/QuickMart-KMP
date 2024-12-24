//
//  CartItemCard.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct CartItemCard: View {
    let imageUrl: String
    let productName: String
    let currentPrice: Double
    let originalPrice: Double
    let quantity: Int
    let isChecked: Bool
    let onQuantityChange: (Int) -> Void
    let onCheckedChange: (Bool) -> Void
    let onDelete: () -> Void

    var body: some View {
        HStack {
            AsyncImage(url: URL(string: imageUrl))
                .frame(width: 100, height: 100)
                .clipShape(RoundedRectangle(cornerRadius: 8))

            VStack(alignment: .leading, spacing: 8) {
                Text(productName)
                    .font(.headline)
                Text("$\(currentPrice, specifier: "%.2f")")
                    .font(.subheadline)
                    .foregroundColor(.secondary)

                if originalPrice > currentPrice {
                    Text("$\(originalPrice, specifier: "%.2f")")
                        .font(.footnote)
                        .strikethrough()
                        .foregroundColor(.gray)
                }

                HStack {
                    QuantitySelector(
                        quantity: quantity,
                        onQuantityChanged: onQuantityChange
                    )
                    Spacer()
                    Button(action: onDelete) {
                        Image(systemName: "trash")
                            .foregroundColor(.red)
                    }
                }
            }
            .padding(.leading, 8)
        }
    }
}

extension CartLocalItemDto: @retroactive Identifiable {}
