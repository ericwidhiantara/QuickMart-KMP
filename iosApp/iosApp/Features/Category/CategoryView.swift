//
//  CategoryListScreen.swift
//  iosApp
//
//  Created by Eric on 24/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Combine
import Shared
import SwiftUI

struct CategoryView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: CategoryListViewModel = KoinHelper()
        .getCategoryListViewModel()
    @State private var state: CategoryState = CategoryState.Idle()

    // Track the last visible item for infinite scroll
    @State private var lastVisibleItem: CategoryEntity? = nil

    let params = CategoryFormParamsEntity(
        query: nil,
        queryBy: nil,
        sortBy: "created_at",
        sortOrder: "desc",
        limit: 10,
        page: 1
    )

    private func onItemAppear(_ item: CategoryEntity) {
        guard let successState = state as? CategoryState.Success else { return }

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
            let newParams = CategoryFormParamsEntity(
                query: params.query,
                queryBy: params.queryBy,
                sortBy: params.sortBy,
                sortOrder: params.sortOrder,
                limit: params.limit,
                page: Int32(nextPage)
            )

            viewModel.fetchCategories(params: newParams, isFirstLoad: false)
        }
    }

    var body: some View {
        let appUiState = viewModel.state

        NavigationView {
            VStack {
                switch state {
                case is CategoryState.LoadingFirstPage:
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)

                case let successState as CategoryState.Success:
                    ScrollView {
                        LazyVGrid(
                            columns: [
                                GridItem(.flexible()),
                                GridItem(.flexible()),
                            ],
                            spacing: 8
                        ) {
                            ForEach(successState.data, id: \.self) { category in
                                CategoryCard(
                                    category: category,
                                    onClick: {
                                        // Handle navigation here
                                    }
                                )
                                .onAppear {
                                    onItemAppear(category)
                                }
                            }

                            if successState.isLoadingMore {
                                GridRow {
                                    ProgressView()
                                        .frame(maxWidth: .infinity)
                                        .padding()
                                        .gridCellColumns(2)
                                }
                            }
                        }
                        .padding(.horizontal, 16)
                    }

                case let errorState as CategoryState.Error:
                    Text(errorState.message)
                        .frame(
                            maxWidth: .infinity, maxHeight: .infinity,
                            alignment: .center)

                default:
                    EmptyView()
                }
            }
            .navigationTitle("Categories")
            .navigationBarTitleDisplayMode(.inline)
            .task {
                appUiState.subscribe { state in
                    self.state = state!
                }
            }
            .onAppear {
                viewModel.fetchCategories(params: params, isFirstLoad: true)
            }
        }
    }
}

// Helper extension to make the grid row implementation cleaner
extension View {
    func gridRow() -> some View {
        GridRow { self }
    }
}
