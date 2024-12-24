//
//  QuantitySelector.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI

struct QuantitySelector: View {
    @State private var quantity: Int
    let onQuantityChanged: (Int) -> Void
    let minValue: Int
    let maxValue: Int

    init(
        quantity: Int,
        onQuantityChanged: @escaping (Int) -> Void,
        minValue: Int = 1,
        maxValue: Int = Int.max
    ) {
        _quantity = State(initialValue: quantity)
        self.onQuantityChanged = onQuantityChanged
        self.minValue = minValue
        self.maxValue = maxValue
    }

    var body: some View {
        HStack {
            Button(action: {
                if quantity > minValue {
                    quantity -= 1
                    onQuantityChanged(quantity)
                }
            }) {
                Text("−")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(quantity > minValue ? .blue : .gray.opacity(0.5))
                    .frame(width: 30, height: 30)
            }
            .disabled(quantity <= minValue)

            Text("\(quantity)")
                .font(.system(size: 20, weight: .medium))
                .padding(.horizontal, 16)

            Button(action: {
                if quantity < maxValue {
                    quantity += 1
                    onQuantityChanged(quantity)
                }
            }) {
                Text("+")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(quantity < maxValue ? .blue : .gray.opacity(0.5))
                    .frame(width: 30, height: 30)
            }
            .disabled(quantity >= maxValue)
        }
    }
}
