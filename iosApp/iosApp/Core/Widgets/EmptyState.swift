//
//  EmptyState.swift
//  iosApp
//
//  Created by Eric on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI

struct EmptyState: View {
    let imageUrl: String?
    let title: String
    let description: String
    let buttonText: String
    let onButtonClick: () -> Void
    
    var body: some View {
        VStack(spacing: 16) {
            Image(imageUrl ?? "EmptyCart")
                .resizable()
                .scaledToFit()
                .frame(width: 120, height: 120)
            
            Text(LocalizedStringKey(title))
                .font(.title)
                .fontWeight(.bold)
            
            Text(LocalizedStringKey(description))
                .font(.body)
                .multilineTextAlignment(.center)
                .padding(.horizontal)
            
            CustomOutlinedButton(
                buttonText: NSLocalizedString(
                    buttonText,
                    comment: ""
                ),
                buttonTextColor: .white,
                buttonContainerColor: .colorCyan,
                onClick: onButtonClick
            ).padding(.horizontal)

        }
        .padding()
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
