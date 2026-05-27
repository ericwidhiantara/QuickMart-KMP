//
//  ShippingAddressFormView.swift
//  iosApp
//

import Shared
import SwiftUI

struct ShippingAddressFormView: View {
    @Binding var rootView: AppScreen
    let editAddressId: String? // nil = create

    @ObservedObject var viewModel: ShippingAddressListViewModel = KoinHelper().getShippingAddressListViewModel()
    @State private var mutationState: AddressMutationState = AddressMutationState.Idle()

    // Form fields
    @State private var label = ""
    @State private var recipientName = ""
    @State private var phone = ""
    @State private var addressLine = ""
    @State private var city = ""
    @State private var province = ""
    @State private var postalCode = ""
    @State private var country = "Indonesia"
    @State private var isDefault = false

    private var isEditMode: Bool { editAddressId != nil }
    private var isLoading: Bool { mutationState is AddressMutationState.Loading }

    var body: some View {
        Form {
            Section("Address Label") {
                TextField("e.g. Home, Office", text: $label)
            }

            Section("Recipient") {
                TextField("Full name", text: $recipientName)
                TextField("Phone number", text: $phone)
                    .keyboardType(.phonePad)
            }

            Section("Address Details") {
                TextField("Street address", text: $addressLine)
                TextField("City", text: $city)
                TextField("Province", text: $province)
                TextField("Postal code", text: $postalCode)
                    .keyboardType(.numberPad)
                TextField("Country", text: $country)
            }

            Section {
                Toggle("Set as default address", isOn: $isDefault)
            }

            Section {
                Button(action: submit) {
                    if isLoading {
                        HStack {
                            ProgressView()
                            Text(isEditMode ? "Updating..." : "Adding...")
                        }
                    } else {
                        Text(isEditMode ? "Update Address" : "Add Address")
                            .frame(maxWidth: .infinity)
                            .fontWeight(.semibold)
                    }
                }
                .disabled(isLoading || !isFormValid)
            }
        }
        .navigationTitle(isEditMode ? "Edit Address" : "New Address")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button("Cancel") { rootView = .shipping_address_list }
            }
        }
        .onAppear {
            observeState()
        }
    }

    private var isFormValid: Bool {
        !label.isEmpty && !recipientName.isEmpty && !phone.isEmpty &&
        !addressLine.isEmpty && !city.isEmpty && !province.isEmpty && !postalCode.isEmpty
    }

    private func submit() {
        if isEditMode, let id = editAddressId {
            viewModel.updateAddress(params: UpdateShippingAddressParamsEntity(
                id: id,
                label: label,
                recipientName: recipientName,
                phone: phone,
                addressLine: addressLine,
                city: city,
                province: province,
                postalCode: postalCode,
                country: country,
                isDefault: KotlinBoolean(bool: isDefault)
            ))
        } else {
            viewModel.createAddress(params: CreateShippingAddressParamsEntity(
                label: label,
                recipientName: recipientName,
                phone: phone,
                addressLine: addressLine,
                city: city,
                province: province,
                postalCode: postalCode,
                country: country,
                isDefault: isDefault
            ))
        }
    }

    private func observeState() {
        viewModel.mutationState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState {
                    self.mutationState = s
                    if s is AddressMutationState.Success {
                        self.rootView = .shipping_address_list
                    }
                }
            }
        }
    }
}
