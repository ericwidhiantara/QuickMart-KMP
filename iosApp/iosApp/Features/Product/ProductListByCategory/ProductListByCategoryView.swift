//
//  ProductListByCategoryView.swift
//  iosApp
//

import Shared
import SwiftUI

struct ProductListByCategoryView: View {
    @Binding var rootView: AppScreen

    let categoryId: String
    let categoryName: String

    @StateObject private var viewModel: ProductListViewModel = KoinHelper().getProductListViewModel()
    @State private var state: ProductState = ProductState.Idle()
    @State private var selectedFilter: String = ""
    @State private var showFilterSheet = false

    private var baseParams: ProductFormParamsEntity {
        ProductFormParamsEntity(
            categoryId: categoryId,
            query: nil,
            queryBy: nil,
            sortBy: sortByFromFilter(selectedFilter),
            sortOrder: sortOrderFromFilter(selectedFilter),
            limit: 10,
            page: 1
        )
    }

    var body: some View {
        NavigationStack {
            VStack(spacing: 0) {
                switch state {
                case is ProductState.LoadingFirstPage:
                    ProgressView().frame(maxWidth: .infinity, maxHeight: .infinity)

                case let s as ProductState.Success:
                    ScrollView {
                        LazyVGrid(
                            columns: [GridItem(.flexible(), spacing: 8), GridItem(.flexible(), spacing: 8)],
                            spacing: 8
                        ) {
                            ForEach(s.data, id: \.self) { product in
                                ProductCard(
                                    product: product,
                                    onClick: { rootView = .product_detail(product.id ?? "") }
                                )
                                .onAppear { onItemAppear(product, state: s) }
                            }

                            if s.isLoadingMore {
                                GridRow {
                                    ProgressView()
                                        .frame(maxWidth: .infinity)
                                        .padding()
                                        .gridCellColumns(2)
                                }
                            }
                        }
                        .padding(16)
                    }

                case let e as ProductState.Error:
                    Text(e.message)
                        .foregroundColor(.secondary)
                        .frame(maxWidth: .infinity, maxHeight: .infinity)

                default:
                    EmptyView()
                }
            }
            .navigationTitle(categoryName.capitalized)
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .topBarLeading) {
                    Button(action: { rootView = .main }) {
                        Image(systemName: "chevron.left")
                    }
                }
                ToolbarItemGroup(placement: .topBarTrailing) {
                    Button(action: { showFilterSheet = true }) {
                        Image("Filter").resizable().scaledToFit().frame(width: 20, height: 20)
                    }
                    Button(action: { rootView = .search }) {
                        Image(systemName: "magnifyingglass")
                    }
                }
            }
        }
        .sheet(isPresented: $showFilterSheet) {
            CustomFilterBottomSheet(
                selectedFilter: $selectedFilter,
                onApply: {
                    showFilterSheet = false
                    viewModel.fetchProducts(params: baseParams, isFirstLoad: true)
                }
            )
            .presentationDetents([.fraction(0.55)])
        }
        .task {
            viewModel.state.subscribe { s in state = s! }
            viewModel.fetchProducts(params: baseParams, isFirstLoad: true)
        }
    }

    private func onItemAppear(_ item: ProductEntity, state: ProductState.Success) {
        let data = state.data
        guard let index = data.firstIndex(where: { $0.id == item.id }),
              index >= data.count - 2,
              !state.isLastPage,
              !state.isLoadingMore else { return }
        viewModel.fetchProducts(params: baseParams, isFirstLoad: false)
    }

    private func sortByFromFilter(_ f: String) -> String {
        switch f {
        case "Price: Low to High", "Price: High to Low": return "price"
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
