//
//  ProductCard.swift
//  iosApp
//
//  Created by Eric on 27/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import Shared
import SwiftUI

struct ProductCard: View {
    let product: ProductEntity
    let onClick: () -> Void

    var body: some View {
        Button(action: {
            onClick()
        }) {
            VStack(alignment: .center) {
                Image("MenuCategories")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 50, height: 50)
                    .foregroundColor(.primary)


                Spacer().frame(height: 8)

                Text(product.name?.capitalized ?? "-")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(.primary)
                    .multilineTextAlignment(.center)
            }
            .padding(8)
            .frame(maxWidth: .infinity)
            .background(Color(.secondarySystemBackground))
            .cornerRadius(8)
            .shadow(color: Color.black.opacity(0.1), radius: 1, x: 0, y: 1)

        }
    }
}

extension ProductEntity: @retroactive Identifiable {}
