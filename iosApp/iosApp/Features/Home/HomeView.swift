//
//  HomeView.swift
//  iosApp
//
//  Created by Eric on 06/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

struct HomeView: View {
    @Binding var rootView: AppScreen
    @StateObject private var userViewModel: UserViewModel = KoinHelper()
        .getUserViewModel()
    @StateObject private var navBarViewModel: NavBarViewModel = KoinHelper()
        .getNavBarViewModel()
    @StateObject private var productViewModel: ProductListViewModel =
        KoinHelper().getProductListViewModel()

    @State private var userState: UserState = UserState.Idle()
    @State private var productState: ProductState = ProductState.Idle()

    let params = ProductFormParamsEntity(
        categoryId: nil,
        query: nil,
        queryBy: nil,
        sortBy: "created_at",
        sortOrder: "desc",
        limit: 4,
        page: 1
    )

    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                // Custom Navigation Bar
                CustomNavigationBar(
                    userState: userState,
                    onSearchTap: { rootView = .search },
                    onProfileTap: { navBarViewModel.updateIndex(index: 4) }
                )
                .padding(.horizontal, 16)
                .padding(.vertical, 10)

                // Content
                VStack(spacing: 24) {
                    // Carousel Section
                    Carousel().padding()

                    // Categories Section
                    VStack(spacing: 12) {
                        HeaderRow(
                            title: "categories",
                            onSeeAllTap: {
                                navBarViewModel.updateIndex(index: 1)
                            }
                        )

                        CategoryList(rootView: $rootView)
                    }.frame(height: 120)
                        .padding(.horizontal, 16)

                    // Latest Products Section
                    VStack(spacing: 12) {
                        HeaderRow(
                            title: "latest_products",
                            onSeeAllTap: { rootView = .product }
                        )

                        ProductGrid(
                            state: productState,
                            onProductTap: { productId in rootView = .product_detail(productId) }
                        )
                    }
                    .padding(.horizontal, 16)

                    Spacer(minLength: 24)
                }
            }
        }
        .task {
            // Subscribe to user state
            userViewModel.userState.subscribe { state in
                self.userState = state!
            }

            // Subscribe to product state
            productViewModel.state.subscribe { state in
                self.productState = state!
            }

            // Initial data fetching
            userViewModel.getUserLogin()
            productViewModel.fetchProducts(
                params: params, isFirstLoad: true)
        }
    }
}

// MARK: - Supporting Views

struct CustomNavigationBar: View {
    let userState: UserState
    let onSearchTap: () -> Void
    let onProfileTap: () -> Void
    @Environment(\.colorScheme) private var colorScheme

    private let themeManager = ThemeManager.shared

    var body: some View {
        HStack {
            // Logo
            if let themeImage = themeManager.getLogoImage(
                isDarkMode: colorScheme == .dark
            ) {
                Image(uiImage: themeImage)
                    .resizable()
                    .scaledToFit()
                    .frame(height: 32)
            }

            Spacer()

            // Search and Profile
            HStack(spacing: 12) {
                Button(action: onSearchTap) {
                    Image(systemName: "magnifyingglass")
                        .foregroundColor(.primary)
                }

                Button(action: onProfileTap) {
                    switch userState {
                    case let success as UserState.Success:
                        ProfileImage(
                            url: success.data.profilePicture
                                ?? "https://via.placeholder.com/48x48")
                    case is UserState.Error, is UserState.Idle:
                        ProfileImage(url: "https://via.placeholder.com/48x48")
                    case is UserState.Loading:
                        ProgressView()
                            .frame(width: 36, height: 36)
                    default:
                        EmptyView()
                    }
                }
                .frame(width: 42)
            }
        }
    }
}

struct HeaderRow: View {
    let title: String
    let onSeeAllTap: () -> Void

    var body: some View {
        HStack {
            Text(LocalizedStringKey(title))
                .font(.system(size: 18, weight: .bold))

            Spacer()

            Button(action: onSeeAllTap) {
                Text(LocalizedStringKey("see_all"))
                    .font(.system(size: 12))
                    .foregroundColor(.accentColor)
            }
        }
    }
}

struct ProductGrid: View {
    let state: ProductState
    let onProductTap: (String) -> Void

    var body: some View {
        switch state {
        case let success as ProductState.Success:
            LazyVGrid(
                columns: [
                    GridItem(.flexible(), spacing: 8),
                    GridItem(.flexible(), spacing: 8),
                ],
                spacing: 8
            ) {
                ForEach(Array(success.data.prefix(4)), id: \.id) { product in
                    ProductCard(
                        product: product,
                        onClick: {
                            onProductTap(product.id!)
                        }
                    )
                }
            }

        case is ProductState.LoadingFirstPage:
            ProgressView()
                .frame(height: 400)

        case let error as ProductState.Error:
            Text(error.message)
                .frame(height: 400)

        default:
            EmptyView()
        }
    }
}
