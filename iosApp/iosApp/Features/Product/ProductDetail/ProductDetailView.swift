//
//  ProductDetailView.swift
//  iosApp
//

import Shared
import SwiftUI

struct ProductDetailView: View {
    @Binding var rootView: AppScreen

    let productId: String

    @StateObject private var viewModel: ProductDetailViewModel = KoinHelper().getProductDetailViewModel()
    @StateObject private var cartViewModel: MyCartViewModel = KoinHelper().getMyCartViewModel()
    @StateObject private var wishlistViewModel: MyWishlistViewModel = KoinHelper().getMyWishlistViewModel()
    @StateObject private var userViewModel: UserViewModel = KoinHelper().getUserViewModel()

    @State private var state: ProductDetailState = ProductDetailState.Idle()
    @State private var userState: UserState = UserState.Idle()
    @State private var wishlistItems: [WishlistLocalItemDto] = []
    @State private var showAddedToCart = false
    @State private var currentImagePage = 0

    private var userId: String {
        (userState as? UserState.Success)?.data.id ?? ""
    }

    private var isFavorite: Bool {
        guard let s = state as? ProductDetailState.Success else { return false }
        return wishlistItems.contains { $0.productId == s.data.id }
    }

    var body: some View {
        ZStack(alignment: .bottom) {
            switch state {
            case is ProductDetailState.Loading:
                ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)

            case let s as ProductDetailState.Success:
                ProductDetailContent(
                    product: s.data,
                    isFavorite: isFavorite,
                    currentPage: $currentImagePage,
                    onBack: { rootView = .main },
                    onToggleWishlist: { toggleWishlist(product: s.data) }
                )
                ProductDetailBottomBar(
                    product: s.data,
                    onBuyNow: { /* TODO: checkout */ },
                    onAddToCart: { addToCart(product: s.data) }
                )

            case let e as ProductDetailState.Error:
                VStack(spacing: 16) {
                    Text(e.message).foregroundColor(.secondary)
                    Button("Back") { rootView = .main }
                }

            default:
                ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .ignoresSafeArea(edges: .top)
        .overlay(alignment: .top) {
            // Back + wishlist top bar
            if let s = state as? ProductDetailState.Success {
                HStack {
                    Button(action: { rootView = .main }) {
                        Image(systemName: "chevron.left")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(.primary)
                            .padding(10)
                            .glassCard(cornerRadius: 20, fallback: Color(.systemBackground))
                    }
                    Spacer()
                    Button(action: { toggleWishlist(product: s.data) }) {
                        Image(systemName: isFavorite ? "heart.fill" : "heart")
                            .font(.system(size: 17, weight: .semibold))
                            .foregroundColor(isFavorite ? .red : .primary)
                            .padding(10)
                            .glassCard(cornerRadius: 20, fallback: Color(.systemBackground))
                    }
                }
                .padding(.horizontal, 16)
                .padding(.top, 56)
            }
        }
        .overlay(alignment: .top) {
            if showAddedToCart {
                Text("Added to cart ✓")
                    .font(.system(size: 14, weight: .medium))
                    .foregroundColor(.white)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 8)
                    .background(Color.green)
                    .cornerRadius(20)
                    .padding(.top, 100)
                    .transition(.move(edge: .top).combined(with: .opacity))
            }
        }
        .animation(.easeInOut(duration: 0.3), value: showAddedToCart)
        .task {
            viewModel.state.subscribe { s in self.state = s! }
            userViewModel.userState.subscribe { s in self.userState = s! }
            wishlistViewModel.wishlistItems.subscribe { items in
                self.wishlistItems = items as? [WishlistLocalItemDto] ?? []
            }
            viewModel.fetchProductDetail(productId: productId)
            userViewModel.getUserLogin()
        }
        .onChange(of: userId) { id in
            if !id.isEmpty {
                wishlistViewModel.fetchWishlistItems(userId: id)
                cartViewModel.fetchCartItems(userId: id)
            }
        }
    }

    private func toggleWishlist(product: ProductEntity) {
        guard !userId.isEmpty else { return }
        let item = WishlistLocalItemDto(
            userId: userId,
            productId: product.id ?? "",
            productName: product.name ?? "",
            productImage: product.images?.first ?? "",
            productPrice: product.variants?.first?.price ?? 0,
            discountPercentage: product.variants?.first?.discountPercentage?.doubleValue ?? 0
        )
        if isFavorite {
            wishlistViewModel.deleteItem(item: item)
        } else {
            wishlistViewModel.addItem(item: item)
        }
        wishlistViewModel.fetchWishlistItems(userId: userId)
    }

    private func addToCart(product: ProductEntity) {
        guard !userId.isEmpty else { return }
        let item = CartLocalItemDto(
            userId: userId,
            productId: product.id ?? "",
            productName: product.name ?? "",
            productImage: product.images?.first ?? "",
            productPrice: product.variants?.first?.price ?? 0,
            discountPercentage: product.variants?.first?.discountPercentage?.doubleValue ?? 0,
            qty: 1,
            selected: true
        )
        cartViewModel.addItem(item: item)
        withAnimation { showAddedToCart = true }
        DispatchQueue.main.asyncAfter(deadline: .now() + 2) {
            withAnimation { showAddedToCart = false }
        }
    }
}

// MARK: - Content

private struct ProductDetailContent: View {
    let product: ProductEntity
    let isFavorite: Bool
    @Binding var currentPage: Int
    let onBack: () -> Void
    let onToggleWishlist: () -> Void

    @State private var isDescExpanded = false

    var body: some View {
        ScrollView {
            VStack(spacing: 0) {
                // Image carousel
                ProductImageCarousel(images: product.images ?? [], currentPage: $currentPage)
                    .frame(height: 320)

                // Info card
                VStack(alignment: .leading, spacing: 16) {
                    // Tags
                    if let tags = product.tags, !tags.isEmpty {
                        ScrollView(.horizontal, showsIndicators: false) {
                            HStack(spacing: 8) {
                                ForEach(Array(tags.enumerated()), id: \.offset) { i, tag in
                                    let colors: [Color] = [.colorCyan, .blue, .orange]
                                    Text(tag.capitalized)
                                        .font(.system(size: 12, weight: .medium))
                                        .foregroundColor(.white)
                                        .padding(.horizontal, 12)
                                        .padding(.vertical, 6)
                                        .background(colors[i % colors.count])
                                        .cornerRadius(12)
                                }
                            }
                        }
                    }

                    // Name + Price
                    HStack(alignment: .top) {
                        Text(product.name?.capitalized ?? "")
                            .font(.system(size: 20, weight: .bold))
                        Spacer()
                        Text(product.variants?.first?.localizedPrice ?? "")
                            .font(.system(size: 20, weight: .bold))
                            .foregroundColor(.colorCyan)
                    }

                    // Brand
                    if let brand = product.brand {
                        Text(brand)
                            .font(.system(size: 14))
                            .foregroundColor(.secondary)
                    }

                    // Description
                    if let desc = product.description_ {
                        VStack(alignment: .leading, spacing: 4) {
                            Text(isDescExpanded ? desc : String(desc.prefix(150)) + (desc.count > 150 ? "..." : ""))
                                .font(.system(size: 14))
                                .foregroundColor(.secondary)
                            if desc.count > 150 {
                                Button(isDescExpanded ? "Show less" : "Show more") {
                                    isDescExpanded.toggle()
                                }
                                .font(.system(size: 13, weight: .medium))
                                .foregroundColor(.colorCyan)
                            }
                        }
                    }
                }
                .padding(20)
                .glassCard(cornerRadius: 24, fallback: Color(.systemBackground))
                .padding(.horizontal, 12)
                .offset(y: -24)

                Spacer(minLength: 100) // space for bottom bar
            }
        }
    }
}

// MARK: - Image Carousel

private struct ProductImageCarousel: View {
    let images: [String]
    @Binding var currentPage: Int

    var body: some View {
        let displayImages = images.isEmpty
            ? ["https://via.placeholder.com/400x320"]
            : images.map { $0 }

        ZStack(alignment: .bottom) {
            TabView(selection: $currentPage) {
                ForEach(Array(displayImages.enumerated()), id: \.offset) { i, url in
                    AsyncImage(url: URL(string: url)) { image in
                        image.resizable().scaledToFill()
                    } placeholder: {
                        Color(.secondarySystemBackground)
                    }
                    .clipped()
                    .tag(i)
                }
            }
            .tabViewStyle(PageTabViewStyle(indexDisplayMode: .never))

            // Page dots
            HStack(spacing: 6) {
                ForEach(0..<displayImages.count, id: \.self) { i in
                    Circle()
                        .fill(i == currentPage ? Color.colorCyan : Color.white.opacity(0.6))
                        .frame(width: i == currentPage ? 8 : 6, height: i == currentPage ? 8 : 6)
                }
            }
            .padding(.bottom, 36)
        }
    }
}

// MARK: - Bottom Bar

private struct ProductDetailBottomBar: View {
    let product: ProductEntity
    let onBuyNow: () -> Void
    let onAddToCart: () -> Void

    var body: some View {
        HStack(spacing: 12) {
            Button(action: onBuyNow) {
                Text(LocalizedStringKey("buy_now"))
                    .font(.system(size: 15, weight: .semibold))
                    .foregroundColor(.colorCyan)
                    .frame(maxWidth: .infinity, minHeight: 48)
                    .overlay(RoundedRectangle(cornerRadius: 12).stroke(Color.colorCyan, lineWidth: 1.5))
            }
            Button(action: onAddToCart) {
                Label(LocalizedStringKey("add_to_cart"), systemImage: "cart.badge.plus")
                    .font(.system(size: 15, weight: .semibold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity, minHeight: 48)
                    .glassButton(tint: .colorCyan, cornerRadius: 12, isEnabled: true, fallback: .colorCyan)
            }
        }
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
        .padding(.bottom, 8)
        .glassBar()
    }
}
