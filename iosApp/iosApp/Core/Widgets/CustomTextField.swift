//
//  CustomTextField.swift
//  iosApp
//
//  Created by Eric on 13/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct CustomTextField: View {
    enum TextFieldType {
        case text
        case password
    }

    // Configuration Properties
    var type: TextFieldType = .text
    var withTitleLabel: Bool = true
    var titleLabel: String?
    var titleLabelFontSize: CGFloat = 14

    // Value and Change Handling
    @Binding var value: String
    var onValueChange: ((String) -> Void)?

    // Validation Properties
    var required: Bool = true
    var validator: ((String) -> Bool)?
    var shouldValidate: Bool = false
    var errorMessage: String?

    // TextField Configuration
    var placeholder: String
    var placeholderFontSize: CGFloat = 14
    var isDisabled: Bool = false
    var isReadOnly: Bool = false

    // State for password visibility
    @State private var isSecureTextEntry: Bool = true

    // Validation State
    private var isInputValid: Bool {
        guard shouldValidate else { return true }
        return !(validator?(value) == false)
    }
    
    var textInputAutocapitalization: TextInputAutocapitalization? = .sentences

    var body: some View {
        VStack(alignment: .leading, spacing: 4) {
            // Title Label
            if withTitleLabel {
                HStack(alignment: .center, spacing: 2) {
                    Text(titleLabel ?? "")
                        .font(.system(size: titleLabelFontSize))

                    if required {
                        Text("*")
                            .foregroundColor(.red)
                            .font(.system(size: titleLabelFontSize))
                    }
                }
            }

            // Input Field
            HStack {
                Group {
                    switch type {
                    case .text:
                        TextField(placeholder, text: $value)
                            .disableAutocorrection(true)
                            .textInputAutocapitalization(textInputAutocapitalization)
                            .frame(height: 48)
                            .padding(.horizontal, 10)

                    case .password:
                        Group {
                            if isSecureTextEntry {
                                SecureField(placeholder, text: $value)
                                    .disableAutocorrection(true)
                                    .textInputAutocapitalization(textInputAutocapitalization)
                                    .frame(height: 48)
                                    .padding(.leading, 10)

                            } else {
                                TextField(placeholder, text: $value)
                                    .disableAutocorrection(true)
                                    .textInputAutocapitalization(textInputAutocapitalization)
                                    .frame(height: 48)
                                    .padding(.horizontal, 10)

                            }
                        }
                    }
                }
                .font(.system(size: placeholderFontSize))
                .disabled(isDisabled)
                .onChange(of: value) { newValue in
                    onValueChange?(newValue)
                }

                // Password visibility toggle for password fields
                if type == .password {
                    Button(action: {
                        isSecureTextEntry.toggle()
                    }) {
                        Image(
                            systemName: isSecureTextEntry ? "eye.slash" : "eye"
                        )
                        .foregroundColor(.gray)
                    }.padding(.horizontal, 10)
                }
            }
            .overlay(
                RoundedRectangle(cornerRadius: 8)
                    .stroke(isInputValid ? Color.gray : Color.red, lineWidth: 1)
            )
            // Error Message
            if required && !isInputValid {
                Text(
                    errorMessage
                        ?? NSLocalizedString(
                            "field_required", comment: "Field Required")
                )
                .foregroundColor(.red)
                .font(.system(size: 12))
            }
        }
    }
}
