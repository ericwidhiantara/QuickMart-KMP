//
//  ShippingAddressView.swift
//  iosApp
//

import Shared
import SwiftUI

struct ShippingAddressView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: ShippingAddressListViewModel = KoinHelper().getShippingAddressListViewModel()

    @State private var listState: AddressListState = AddressListState.Idle()
    @State private var mutationState: AddressMutationState = AddressMutationState.Idle()
    @State private var showDeleteConfirm = false
    @State private var addressToDelete: ShippingAddressEntity? = nil

    var body: some View {
        ZStack {
            if #available(iOS 26, *) {
                Color.clear
            } else {
                Color(.systemGroupedBackground).ignoresSafeArea()
            }

            Group {
                if let _ = listState as? AddressListState.Loading {
                    ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)
                } else if let success = listState as? AddressListState.Success {
                    if success.addresses.isEmpty {
                        emptyView
                    } else {
                        ScrollView {
                            LazyVStack(spacing: 12) {
                                ForEach(success.addresses, id: \.id) { address in
                                    AddressCard(
                                        address: address,
                                        onSetDefault: {
                                            if let id = address.id {
                                                viewModel.setDefault(id: id)
                                            }
                                        },
                                        onEdit: {
                                            rootView = .shipping_address_form(address.id)
                                        },
                                        onDelete: {
                                            addressToDelete = address
                                            showDeleteConfirm = true
                                        }
                                    )
                                }
                            }
                            .padding(16)
                        }
                    }
                } else if let error = listState as? AddressListState.Error {
                    VStack(spacing: 12) {
                        Image(systemName: "exclamationmark.circle")
                            .font(.system(size: 44))
                            .foregroundColor(.red)
                        Text(error.message)
                        Button("Retry") { viewModel.fetchAddresses() }
                            .buttonStyle(.borderedProminent)
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .padding()
                } else {
                    Color.clear
                }
            }
        }
        .navigationTitle("Shipping Addresses")
        .navigationBarTitleDisplayMode(.large)
        .toolbar {
            ToolbarItem(placement: .navigationBarTrailing) {
                Button(action: { rootView = .shipping_address_form(nil) }) {
                    Image(systemName: "plus")
                }
            }
        }
        .confirmationDialog("Delete Address", isPresented: $showDeleteConfirm, titleVisibility: .visible) {
            Button("Delete", role: .destructive) {
                if let id = addressToDelete?.id {
                    viewModel.deleteAddress(id: id)
                }
            }
            Button("Cancel", role: .cancel) {}
        } message: {
            Text("This address will be permanently deleted.")
        }
        .onAppear {
            viewModel.fetchAddresses()
            observeStates()
        }
    }

    private var emptyView: some View {
        VStack(spacing: 16) {
            Image(systemName: "map")
                .font(.system(size: 60))
                .foregroundColor(.secondary)
            Text("No addresses saved")
                .font(.headline)
            Text("Add a shipping address to get started.")
                .font(.subheadline)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
            Button("Add Address") {
                rootView = .shipping_address_form(nil)
            }
            .buttonStyle(.borderedProminent)
        }
        .padding()
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private func observeStates() {
        viewModel.listState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState { self.listState = s }
            }
        }
        viewModel.mutationState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState { self.mutationState = s }
            }
        }
    }
}

// MARK: - AddressCard

struct AddressCard: View {
    let address: ShippingAddressEntity
    let onSetDefault: () -> Void
    let onEdit: () -> Void
    let onDelete: () -> Void

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            HStack {
                HStack(spacing: 6) {
                    Text(address.label ?? "Address")
                        .font(.headline)
                    if address.isDefault {
                        Text("Default")
                            .font(.caption)
                            .fontWeight(.semibold)
                            .padding(.horizontal, 8)
                            .padding(.vertical, 3)
                            .background(Color.accentColor.opacity(0.15))
                            .foregroundColor(.accentColor)
                            .clipShape(Capsule())
                    }
                }
                Spacer()
                Menu {
                    if !address.isDefault {
                        Button("Set as Default", action: onSetDefault)
                    }
                    Button("Edit", action: onEdit)
                    Button("Delete", role: .destructive, action: onDelete)
                } label: {
                    Image(systemName: "ellipsis.circle")
                        .font(.title3)
                        .foregroundColor(.secondary)
                }
            }

            Divider()

            VStack(alignment: .leading, spacing: 4) {
                Text(address.recipientName ?? "")
                    .font(.subheadline)
                    .fontWeight(.medium)
                Text(address.phone ?? "")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                Text([address.addressLine, address.city, address.province, address.postalCode, address.country]
                    .compactMap { $0 }
                    .filter { !$0.isEmpty }
                    .joined(separator: ", ")
                )
                .font(.subheadline)
                .foregroundColor(.secondary)
                .lineLimit(2)
            }
        }
        .padding(16)
        .glassCard(cornerRadius: 14)
    }
}
