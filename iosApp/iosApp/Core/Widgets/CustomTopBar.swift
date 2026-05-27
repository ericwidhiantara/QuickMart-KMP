//
//  CustomTopBar.swift
//  iosApp
//
//  Created by Eric on 17/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI

struct CustomTopBar: View {
    let title: String
    let showBackButton: Bool
    let actions: AnyView?
    let onBackTap: () -> Void
    
    init(
        title: String, 
        showBackButton: Bool = true, 
        actions: AnyView? = nil, 
        onBackTap: @escaping () -> Void = {}
    ) {
        self.title = title
        self.showBackButton = showBackButton
        self.actions = actions
        self.onBackTap = onBackTap
    }
    
    var body: some View {
        VStack(spacing: 0) {
            HStack {
                // Back Button Section
                if showBackButton {
                    Button(action: onBackTap) {
                        Image(systemName: "chevron.left")
                            .foregroundColor(.primary)
                    }
                }

                // Title Section
                Spacer()
                Text(title)
                    .font(.headline)
                Spacer()

                // Actions Section
                actions ?? AnyView(EmptyView())
            }
            .padding()

            // Divider only on older iOS — glass provides visual separation on iOS 26+
            if #available(iOS 26, *) {
                EmptyView()
            } else {
                Divider()
            }
        }
        .glassBar()
    }
}
