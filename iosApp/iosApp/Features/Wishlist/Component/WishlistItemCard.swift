//
//  WishlistItemCard.swift
//  iosApp
//
//  Created by Eric on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared
import SwiftUI

struct WishlistItemCard: View {
    let imageUrl: String
    let productName: String
    let currentPrice: Double
    let originalPrice: Double
    let onDelete: () -> Void

    var body: some View {
        HStack(spacing: 8) {
            AsyncImage(url: URL(string: imageUrl)) { image in
                image
                    .resizable()
                    .scaledToFill()
                    .frame(width: 120, height: 120)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            } placeholder: {
                Color.gray
                    .frame(width: 120, height: 120)
                    .clipShape(RoundedRectangle(cornerRadius: 12))
            }

            VStack(alignment: .leading, spacing: 8) {
                HStack {
                    Text(productName)
                        .fontWeight(.medium)
                        .lineLimit(1)
                        .truncationMode(.tail)

                    Spacer()

                    Button(action: onDelete) {
                        Image(systemName: "trash")
                            .foregroundColor(.red)
                    }
                }

                Text(String(format: "$%.2f", currentPrice))
                    .fontWeight(.medium)

                if currentPrice != originalPrice {
                    Text(String(format: "$%.2f", originalPrice))
                        .font(.caption)
                        .strikethrough()
                }
            }
        }
        .padding()
        .background(Color(.systemGray6))
        .cornerRadius(12)
    }
}

extension WishlistLocalItemDto: @retroactive Identifiable {}
