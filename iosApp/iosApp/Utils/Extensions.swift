//
//  Extensions.swift
//  iosApp
//
//  Created by Eric on 13/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//
import SwiftUI

extension View {

  func toastView(toast: Binding<Toast?>) -> some View {
    self.modifier(ToastModifier(toast: toast))
  }
}
