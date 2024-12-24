//
//  MyCartScreen.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

struct MyCartView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: MyCartViewModel = KoinHelper()
        .getMyCartViewModel()
    @ObservedObject var userViewModel: UserViewModel = KoinHelper()
        .getUserViewModel()
    @ObservedObject var navbarViewModel: NavBarViewModel = KoinHelper()
        .getNavBarViewModel()

    @State private var userState: UserState = UserState.Idle()
    @State private var cartItems: [CartLocalItemDto] = []

    @State private var showBottomSheet = false
    @State private var selectedVoucher = ""
    @State private var showDeleteDialog = false
    @State private var selectedItem: CartLocalItemDto?

    @State private var cancellables: Set<AnyCancellable> = []

    private func handleUserState(_ state: UserState) {
        switch state {
        case let user as UserState.Success:
            viewModel.fetchCartItems(userId: user.data.id)
            viewModel.fetchSelectedItems(userId: user.data.id)
            observeCartItems()
        default:
            break
        }
    }

    private func observeCartItems() {
        let publisher = createPublisher(viewModel.cartItems)

        publisher
            .receive(on: DispatchQueue.main)
            .sink { items in
                if let cartItems = items as? [CartLocalItemDto] {
                    self.cartItems = cartItems
                }
            }
            .store(in: &cancellables)
    }

    private var emptyStateView: some View {
        EmptyState(
            imageUrl: "EmptyCart",
            title: "empty_cart",
            description: "empty_cart_desc",
            buttonText: "explore_products",
            onButtonClick: {
                // Navigate to explore products screen
            }
        )
    }

    private var cartlistListView: some View {
        VStack(spacing: 0) {
            List {
                ForEach(cartItems) { item in
                    CartItemCard(
                        imageUrl: item.productImage,
                        productName: item.productName,
                        currentPrice: item.productPrice
                            - (item.productPrice * item.discountPercentage / 100),
                        originalPrice: item.productPrice,
                        quantity: Int(item.qty),
                        isChecked: item.selected,
                        onQuantityChange: { newQuantity in

                            viewModel.updateItem(
                                cartItem: item
                            )
                        },
                        onCheckedChange: { isChecked in
                            viewModel.updateItem(
                                cartItem: item)
                        },
                        onDelete: {
                            showDeleteDialog = true
                        }
                    )
                }
            }
            CartSummaryBar(
                shippingCost: 0.0,
                subtotal: viewModel.subtotal.value as! Double,
                onCheckout: {
                    // Handle checkout
                }
            )
        }
    }

    var body: some View {

        let userUiState = userViewModel.userState

        NavigationView {
            VStack {
                if cartItems.isEmpty {
                    emptyStateView
                } else {
                    cartlistListView
                }
            }
            .navigationTitle("my_cart")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                if !cartItems.isEmpty {
                    Button(action: {
                        showBottomSheet = true
                    }) {
                        Text("voucher_code")
                    }
                }
            }
            .sheet(isPresented: $showBottomSheet) {
                VoucherCodeBottomSheet { code in
                    selectedVoucher = code
                    showBottomSheet = false
                }
            }
            .confirmationDialog(
                "confirm_delete_desc",
                isPresented: $showDeleteDialog,
                actions: {
                    Button("delete", role: .destructive) {
                        if let item = selectedItem {
                            // Delete logic here
                            cartItems.removeAll { $0.id == item.id }
                            selectedItem = nil
                        }
                    }
                    Button("cancel", role: .cancel) {}
                }
            )
        }
        
        .onAppear {
            userUiState.subscribe { state in
                self.userState = state!
            }
            userViewModel.getUserLogin()
        }
        .onChange(of: userState) { state in
            handleUserState(state)
        }
    }
}
