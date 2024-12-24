//
//  MyWishlistScreen.swift
//  iosApp
//
//  Created by Eric on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

struct MyWishlistView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: MyWishlistViewModel = KoinHelper()
        .getMyWishlistViewModel()
    @ObservedObject var userViewModel: UserViewModel = KoinHelper()
        .getUserViewModel()
    @ObservedObject var navbarViewModel: NavBarViewModel = KoinHelper()
        .getNavBarViewModel()

    @State private var userState: UserState = UserState.Idle()
    @State private var wishlistItems: [WishlistLocalItemDto] = []
    @State private var showDeleteDialog: Bool = false
    @State private var selectedItem: WishlistLocalItemDto?
    @State private var cancellables: Set<AnyCancellable> = []

    private func handleUserState(_ state: UserState) {
        switch state {
        case let user as UserState.Success:
            viewModel.fetchWishlistItems(userId: user.data.id)
            observeWishlistItems()
        default:
            break
        }
    }

    private func observeWishlistItems() {
        let publisher = createPublisher(viewModel.wishlistItems)

        publisher
            .receive(on: DispatchQueue.main)
            .sink { items in
                if let wishlistItems = items as? [WishlistLocalItemDto] {
                    self.wishlistItems = wishlistItems
                }
            }
            .store(in: &cancellables)
    }

    private var emptyStateView: some View {
        EmptyState(
            imageUrl: "EmptyWishlist",
            title: "empty_wishlist",
            description: "empty_wishlist_desc",
            buttonText: "explore_products",
            onButtonClick: {
                // Navigate to explore products screen
            }
        )
    }

    private var wishlistListView: some View {
        List {
            ForEach(wishlistItems) { item in
                WishlistItemCard(
                    imageUrl: item.productImage,
                    productName: item.productName,
                    currentPrice: item.productPrice
                        - (item.productPrice * item.discountPercentage / 100),
                    originalPrice: item.productPrice,
                    onDelete: {
                        selectedItem = item
                        showDeleteDialog = true
                    }
                )
            }
            .onDelete { indexSet in
                indexSet.forEach { index in
                    // Handle item deletion logic here
                    wishlistItems.remove(at: index)
                }
            }
        }
    }

    var body: some View {
        let userUiState = userViewModel.userState

        NavigationView {
            VStack {
                if wishlistItems.isEmpty {
                    emptyStateView
                } else {
                    wishlistListView
                }
            }
            .navigationTitle("menu_wishlist")
            .navigationBarTitleDisplayMode(.inline)
            .confirmationDialog(
                "confirm_delete_desc",
                isPresented: $showDeleteDialog,
                actions: {
                    Button("delete", role: .destructive) {
                        if let item = selectedItem {
                            // Delete logic here
                            wishlistItems.removeAll { $0.id == item.id }
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
