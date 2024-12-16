//
//  CustomActivityIndicator.swift
//  iosApp
//
//  Created by Eric on 16/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomActivityIndicatorModifier: AnimatableModifier {
    var isLoading: Bool

    init(isLoading: Bool, color: Color = .primary, lineWidth: CGFloat = 3) {
        self.isLoading = isLoading
    }

    var animatableData: Bool {
        get { isLoading }
        set { isLoading = newValue }
    }

    func body(content: Content) -> some View {
        ZStack {
            if isLoading {
                GeometryReader { geometry in
                    ZStack(alignment: .center) {
                        content
                            .disabled(self.isLoading)
                            .blur(radius: self.isLoading ? 2 : 0)
                        VStack {
                            Text("loading")
                            Text("please_wait")
                            ProgressView().progressViewStyle(
                                CircularProgressViewStyle(tint: .white)
                            )
                            
                        }
                        .frame(width: geometry.size.width / 2,
                               height: geometry.size.height / 5)
                        .background(Color.secondary.opacity(0.9))
                        .foregroundColor(Color.white)
                        .cornerRadius(20)
//                        .opacity(self.isLoading ? 1 : 0)
                        .position(x: geometry.frame(in: .local).midX, y: geometry.frame(in: .local).midY)
                    }
                }
            } else {
                content
            }
        }
    }
}
