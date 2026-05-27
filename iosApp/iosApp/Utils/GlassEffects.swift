//
//  GlassEffects.swift
//  iosApp
//
//  Liquid Glass helpers — iOS 26+ gets glass, older iOS gets solid fallbacks.
//

import SwiftUI

// MARK: - Modifiers

struct GlassCardModifier: ViewModifier {
    var cornerRadius: CGFloat = 12
    var fallback: Color = Color(.secondarySystemBackground)

    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: RoundedRectangle(cornerRadius: cornerRadius))
        } else {
            content
                .background(fallback)
                .cornerRadius(cornerRadius)
        }
    }
}

struct GlassBarModifier: ViewModifier {
    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(.regular, in: Rectangle())
        } else {
            content
                .background(Color(.systemBackground))
        }
    }
}

struct GlassSheetModifier: ViewModifier {
    var topCornerRadius: CGFloat = 16

    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(
                    .regular,
                    in: UnevenRoundedRectangle(
                        cornerRadii: RectangleCornerRadii(
                            topLeading: topCornerRadius,
                            bottomLeading: 0,
                            bottomTrailing: 0,
                            topTrailing: topCornerRadius
                        )
                    )
                )
        } else {
            content
                .background(Color(.systemBackground))
        }
    }
}

struct GlassButtonModifier: ViewModifier {
    var tint: Color = .blue
    var cornerRadius: CGFloat = 10
    var isEnabled: Bool = true
    var fallbackEnabled: Color = .blue
    var fallbackDisabled: Color = Color.gray.opacity(0.3)

    func body(content: Content) -> some View {
        if #available(iOS 26, *) {
            content
                .glassEffect(
                    isEnabled
                        ? Glass.regular.interactive().tint(tint)
                        : Glass.regular,
                    in: RoundedRectangle(cornerRadius: cornerRadius)
                )
        } else {
            content
                .background(isEnabled ? fallbackEnabled : fallbackDisabled)
                .cornerRadius(cornerRadius)
        }
    }
}

// MARK: - View Extensions

extension View {
    /// Glass card surface. Replaces manual .background + .cornerRadius + .shadow.
    func glassCard(cornerRadius: CGFloat = 12, fallback: Color = Color(.secondarySystemBackground)) -> some View {
        modifier(GlassCardModifier(cornerRadius: cornerRadius, fallback: fallback))
    }

    /// Glass bar — for nav bars, top bars, sticky headers.
    func glassBar() -> some View {
        modifier(GlassBarModifier())
    }

    /// Glass bottom sheet surface.
    func glassSheet(topCornerRadius: CGFloat = 16) -> some View {
        modifier(GlassSheetModifier(topCornerRadius: topCornerRadius))
    }

    /// Glass button. Replaces solid background + border.
    func glassButton(
        tint: Color = .blue,
        cornerRadius: CGFloat = 10,
        isEnabled: Bool = true,
        fallback: Color = .blue,
        fallbackDisabled: Color = Color.gray.opacity(0.3)
    ) -> some View {
        modifier(GlassButtonModifier(
            tint: tint,
            cornerRadius: cornerRadius,
            isEnabled: isEnabled,
            fallbackEnabled: fallback,
            fallbackDisabled: fallbackDisabled
        ))
    }
}
