//
//  CategoryCard.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct CategoryCard: View {
    let category: CategoryEntity
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

                Text(category.name?.capitalized ?? "-")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(.primary)
                    .multilineTextAlignment(.center)
            }
            .padding(8)
            .frame(maxWidth: .infinity)
            .glassCard(cornerRadius: 8, fallback: Color(.secondarySystemBackground))

        }
    }
}

extension CategoryEntity: @retroactive Identifiable {}
