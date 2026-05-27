//
//  FilterBottomSheet.swift
//  iosApp
//
//  Created by Eric on 27/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//


import SwiftUI

struct CustomFilterBottomSheet: View {
    @Binding var selectedFilter: String
    let onApply: () -> Void
    
    // Filter options
    private let filters = [
        "Price: Low to High",
        "Price: High to Low",
        "Name: A to Z",
        "Name: Z to A"
    ]
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            // Header
            Text("filter")
                .font(.system(size: 16, weight: .medium))
                .padding(.horizontal, 16)
                .padding(.top, 16)
            
            // Filter options
            VStack(spacing: 0) {
                ForEach(filters, id: \.self) { filter in
                    VStack(spacing: 0) {
                        // Filter row
                        HStack(spacing: 12) {
                            // Checkbox
                            Button(action: {
                                selectedFilter = filter
                            }) {
                                ZStack {
                                    RoundedRectangle(cornerRadius: 6)
                                        .strokeBorder(Color.gray.opacity(0.3), lineWidth: 1.5)
                                        .background(
                                            selectedFilter == filter ? Color.blue : Color.white
                                        )
                                        .clipShape(RoundedRectangle(cornerRadius: 6))
                                        .frame(width: 32, height: 32)
                                    
                                    if selectedFilter == filter {
                                        Image(systemName: "checkmark")
                                            .foregroundColor(.white)
                                    }
                                }
                            }
                            
                            // Filter text
                            Text(filter)
                                .font(.system(size: 14, weight: .medium))
                            
                            Spacer()
                        }
                        .frame(height: 56)
                        .padding(.horizontal, 16)
                        
                        // Divider
                        Divider()
                    }
                }
            }
            
            // Spacer and Apply button
            VStack(spacing: 24) {
                // Apply button
                CustomOutlinedButton(
                    buttonText: "apply",
                    onClick: onApply
                )
                
                Spacer()
                    .frame(height: 24)
            }
            .padding(.horizontal, 16)
            .padding(.top, 24)
        }
        .glassSheet(topCornerRadius: 16)
    }
}
