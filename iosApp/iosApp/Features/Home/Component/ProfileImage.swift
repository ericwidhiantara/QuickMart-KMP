//
//  ProfileImage.swift
//  iosApp
//
//  Created by Eric on 06/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import SwiftUI

struct ProfileImage: View {
    let url: String
    var width: CGFloat = 36
    var height: CGFloat = 36
    var colorFilter: Color? = nil
    
    var body: some View {
        AsyncImage(url: URL(string: url)) { phase in
            switch phase {
            case .success(let image):
                image
                    .resizable()
                    .scaledToFill()
                    .frame(width: width, height: height)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                    .if(colorFilter != nil) { view in
                        view.colorMultiply(colorFilter!)
                    }
                
            case .empty, .failure:
                // Placeholder for failed or loading state
                Color.gray
                    .frame(width: width, height: height)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
                
            @unknown default:
                Color.gray
                    .frame(width: width, height: height)
                    .clipShape(RoundedRectangle(cornerRadius: 8))
            }
        }
    }
}

// Helper extension for conditional modifiers
extension View {
    @ViewBuilder func `if`<Content: View>(_ condition: Bool, transform: (Self) -> Content) -> some View {
        if condition {
            transform(self)
        } else {
            self
        }
    }
}
