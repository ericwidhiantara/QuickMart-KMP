//
//  VoucherCodeBottomSheet.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Shared
import SwiftUI

struct VoucherCodeBottomSheet: View {
    @State private var voucherCode: String = ""
    let onApply: (String) -> Void
    
    var body: some View {
        VStack {
            Text("Enter Voucher Code")
                .font(.headline)
            TextField("Voucher Code", text: $voucherCode)
                .padding()
                .background(Color.gray.opacity(0.2))
                .cornerRadius(8)
            
            Button(action: {
                onApply(voucherCode)
            }) {
                Text("apply")
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(Color.blue)
                    .foregroundColor(.white)
                    .cornerRadius(8)
            }
        }
        .padding()
    }
}
