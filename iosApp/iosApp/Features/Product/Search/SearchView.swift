//
//  SearchView.swift
//  iosApp
//

import Shared
import SwiftUI

struct SearchView: View {
    @Binding var rootView: AppScreen

    @StateObject private var viewModel: ProductListViewModel = KoinHelper().getProductListViewModel()
    @State private var query: String = ""
    @State private var selectedFilter: String = ""
    @State private var showFilterSheet = false
    @State private var productState: ProductState = ProductState.Idle()

    private let recentSearches = ["Laptop", "iPhone", "Tablet", "Television", "Airpods"]

    // Debounce search
    @State private var searchTask: Task<Void, Never>? = nil

    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                // Search bar row
                HStack(spacing: 12) {
                    HStack(spacing: 8) {
                        Image(systemName: "magnifyingglass")
                            .foregroundColor(.secondary)
                        TextField(LocalizedStringKey("search"), text: $query)
                            .autocorrectionDisabled()
                            .textInputAutocapitalization(.never)
                            .onChange(of: query) { _ in triggerSearch() }
                    }
                    .padding(10)
                    .glassCard(cornerRadius: 12, fallback: Color(.secondarySystemBackground))

                    Button(action: { showFilterSheet = true }) {
                        Image("Filter")
                            .resizable()
                            .scaledToFit()
                            .frame(width: 22, height: 22)
                            .padding(10)
                            .glassCard(cornerRadius: 12, fallback: Color(.secondarySystemBackground))
                    }

                    Button(action: { rootView = .main }) {
                        Image(systemName: "xmark")
                            .foregroundColor(.primary)
                            .padding(10)
                            .glassCard(cornerRadius: 12, fallback: Color(.secondarySystemBackground))
                    }
                }
                .padding(.horizontal, 16)
                .padding(.vertical, 12)

                Divider()

                // Body
                if query.isEmpty {
                    RecentSearchesView(
                        items: recentSearches,
                        onSelect: { query = $0; triggerSearch() }
                    )
                } else {
                    SearchResultsView(
                        state: productState,
                        onProductTap: { id in rootView = .product_detail(id) }
                    )
                }
            }
            .navigationBarHidden(true)
        }
        .sheet(isPresented: $showFilterSheet) {
            CustomFilterBottomSheet(
                selectedFilter: $selectedFilter,
                onApply: {
                    showFilterSheet = false
                    triggerSearch()
                }
            )
            .presentationDetents([.fraction(0.55)])
        }
        .task {
            viewModel.state.subscribe { s in productState = s! }
        }
    }

    private func triggerSearch() {
        searchTask?.cancel()
        guard !query.trimmingCharacters(in: .whitespaces).isEmpty else { return }
        searchTask = Task {
            try? await Task.sleep(nanoseconds: 400_000_000) // 400ms debounce
            guard !Task.isCancelled else { return }
            let params = ProductFormParamsEntity(
                categoryId: nil,
                query: query,
                queryBy: "name",
                sortBy: sortByFromFilter(selectedFilter),
                sortOrder: sortOrderFromFilter(selectedFilter),
                limit: 20,
                page: 1
            )
            viewModel.fetchProducts(params: params, isFirstLoad: true)
        }
    }

    private func sortByFromFilter(_ f: String) -> String {
        switch f {
        case "Name: A to Z", "Name: Z to A": return "title"
        default: return "created_at"
        }
    }

    private func sortOrderFromFilter(_ f: String) -> String {
        switch f {
        case "Price: Low to High", "Name: A to Z": return "asc"
        default: return "desc"
        }
    }
}

// MARK: - Recent Searches

private struct RecentSearchesView: View {
    let items: [String]
    let onSelect: (String) -> Void

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 0) {
                Text("RECENT SEARCH")
                    .font(.system(size: 13, weight: .semibold))
                    .foregroundColor(.secondary)
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)

                ForEach(items, id: \.self) { item in
                    Button(action: { onSelect(item) }) {
                        HStack {
                            Image(systemName: "clock.arrow.circlepath")
                                .foregroundColor(.secondary)
                                .frame(width: 24)
                            Text(item)
                                .foregroundColor(.primary)
                            Spacer()
                            Image(systemName: "arrow.up.left")
                                .foregroundColor(.secondary)
                        }
                        .padding(.horizontal, 16)
                        .padding(.vertical, 14)
                    }
                    Divider().padding(.leading, 56)
                }
            }
        }
    }
}

// MARK: - Search Results

private struct SearchResultsView: View {
    let state: ProductState
    let onProductTap: (String) -> Void

    var body: some View {
        switch state {
        case is ProductState.LoadingFirstPage:
            ProgressView()
                .frame(maxWidth: .infinity, maxHeight: .infinity)

        case let s as ProductState.Success:
            if s.data.isEmpty {
                EmptyStateView()
            } else {
                ScrollView {
                    LazyVGrid(
                        columns: [GridItem(.flexible(), spacing: 8), GridItem(.flexible(), spacing: 8)],
                        spacing: 8
                    ) {
                        ForEach(s.data, id: \.self) { product in
                            ProductCard(product: product, onClick: { onProductTap(product.id ?? "") })
                        }
                    }
                    .padding(16)
                }
            }

        case let e as ProductState.Error:
            Text(e.message)
                .foregroundColor(.secondary)
                .frame(maxWidth: .infinity, maxHeight: .infinity)

        default:
            EmptyView()
        }
    }
}

private struct EmptyStateView: View {
    var body: some View {
        VStack(spacing: 12) {
            Image(systemName: "magnifyingglass")
                .font(.system(size: 48))
                .foregroundColor(.secondary)
            Text("No results found")
                .font(.system(size: 16, weight: .medium))
                .foregroundColor(.secondary)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}
