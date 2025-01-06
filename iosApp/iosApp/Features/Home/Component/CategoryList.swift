//
//  CategoryList.swift
//  iosApp
//
//  Created by Eric on 06/01/25.
//  Copyright © 2025 orgName. All rights reserved.
//


import Combine
import Shared
import SwiftUI

struct CategoryList: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: CategoryListViewModel = KoinHelper()
        .getCategoryListViewModel()
    @State private var state: CategoryState = CategoryState.Idle()
    
    let params = CategoryFormParamsEntity(
        query: nil,
        queryBy: nil,
        sortBy: "created_at",
        sortOrder: "desc",
        limit: 10,
        page: 1
    )
    
    var body: some View {
        let appUiState = viewModel.state
        
        NavigationView {
            VStack {
                CategoryContentView(
                    state: state,
                    onItemAppear: onItemAppear,
                    onCategoryClick: { category in
                        
                    }
                )
            }
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
    
    private func onItemAppear(_ item: CategoryEntity) {
        guard let successState = state as? CategoryState.Success else { return }
        
        let data = successState.data
        let isLastPage = successState.isLastPage
        let isLoadingMore = successState.isLoadingMore
        
        if let index = data.firstIndex(where: { $0.id == item.id }),
           index >= data.count - 2,
           !isLastPage && !isLoadingMore {
            
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
}

// Separate view for category content
struct CategoryContentView: View {
    let state: CategoryState
    let onItemAppear: (CategoryEntity) -> Void
    let onCategoryClick: (CategoryEntity) -> Void
    
    var body: some View {
        switch state {
        case is CategoryState.LoadingFirstPage:
            LoadingView()
            
        case let successState as CategoryState.Success:
            CategoryListContent(
                data: successState.data,
                isLoadingMore: successState.isLoadingMore,
                onItemAppear: onItemAppear,
                onCategoryClick: onCategoryClick
            )
            
        case let errorState as CategoryState.Error:
            ErrorView(message: errorState.message)
            
        default:
            EmptyView()
        }
    }
}

// Separate view for loading state
struct LoadingView: View {
    var body: some View {
        ProgressView()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
    }
}

// Separate view for error state
struct ErrorView: View {
    let message: String
    
    var body: some View {
        Text(message)
            .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    }
}

// Separate view for category list content
struct CategoryListContent: View {
    let data: [CategoryEntity]
    let isLoadingMore: Bool
    let onItemAppear: (CategoryEntity) -> Void
    let onCategoryClick: (CategoryEntity) -> Void
    
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack(spacing: 8) {
                ForEach(Array(data.prefix(4)), id: \.self) { category in
                    CategoryCard(
                        category: category,
                        onClick: {
                            onCategoryClick(category)
                        }
                    )
                    .frame(width: UIScreen.main.bounds.width / 4.5)
                    .onAppear {
                        onItemAppear(category)
                    }
                }
                
                if isLoadingMore {
                    ProgressView()
                        .frame(width: 50)
                        .padding()
                }
            }
            .padding(.horizontal, 16)
        }
    }
}
