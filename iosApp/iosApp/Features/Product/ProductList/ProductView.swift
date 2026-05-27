//
//  ProductView.swift
//  iosApp
//
//  Created by Eric on 27/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

struct ProductView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: ProductListViewModel = KoinHelper()
        .getProductListViewModel()
    @State private var state: ProductState = ProductState.Idle()

    // Track the last visible item for infinite scroll
    @State private var lastVisibleItem: ProductEntity? = nil

    let params = ProductFormParamsEntity(
        categoryId: nil,
        query: nil,
        queryBy: nil,
        sortBy: "created_at",
        sortOrder: "desc",
        limit: 10,
        page: 1
    )

    private func onItemAppear(_ item: ProductEntity) {
        guard let successState = state as? ProductState.Success else { return }

        // Now we can access the properties directly
        let data = successState.data
        let isLastPage = successState.isLastPage
        let isLoadingMore = successState.isLoadingMore

        // Check if this is one of the last two items
        if let index = data.firstIndex(where: { $0.id == item.id }),
            index >= data.count - 2,
            !isLastPage && !isLoadingMore
        {

            // Update params for next page
            let nextPage = (data.count / Int(params.limit)) + 1
            let newParams = ProductFormParamsEntity(
                categoryId: params.categoryId,
                query: params.query,
                queryBy: params.queryBy,
                sortBy: params.sortBy,
                sortOrder: params.sortOrder,
                limit: params.limit,
                page: Int32(nextPage)
            )

            viewModel.fetchProducts(params: newParams, isFirstLoad: false)
        }
    }

    var body: some View {
        let appUiState = viewModel.state

        NavigationStack {
            VStack {
                switch state {
                case is ProductState.LoadingFirstPage:
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)

                case let successState as ProductState.Success:
                    ScrollView {
                        LazyVGrid(
                            columns: [
                                GridItem(.flexible()),
                                GridItem(.flexible()),
                            ],
                            spacing: 8
                        ) {
                            ForEach(successState.data, id: \.self) { product in
                                ProductCard(
                                    product: product,
                                    onClick: { rootView = .product_detail(product.id ?? "") }
                                )
                                .onAppear {
                                    onItemAppear(product)
                                }
                            }

                            if successState.isLoadingMore {
                                GridRow {
                                    ProgressView()
                                        .frame(maxWidth: .infinity)
                                        .padding()
                                }
                            }
                        }
                        .padding(.horizontal, 16)
                    }

                case let errorState as ProductState.Error:
                    Text(errorState.message)
                        .frame(
                            maxWidth: .infinity, maxHeight: .infinity,
                            alignment: .center)

                default:
                    EmptyView()
                }
            }
            .navigationTitle("products")
            .navigationBarTitleDisplayMode(.inline)
            .task {
                appUiState.subscribe { state in
                    self.state = state!
                }
            }
            .onAppear {
                viewModel.fetchProducts(params: params, isFirstLoad: true)
            }
        }
    }
}
