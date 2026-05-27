//
//  OrderListView.swift
//  iosApp
//

import Shared
import SwiftUI

struct OrderListView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: OrderListViewModel = KoinHelper().getOrderListViewModel()

    @State private var state: OrderListState = OrderListState.Idle()
    @State private var selectedStatus: String? = nil

    private let statusOptions: [(label: String, value: String?)] = [
        ("All", nil),
        ("Pending", "pending"),
        ("Completed", "completed"),
        ("Canceled", "canceled"),
    ]

    var body: some View {
        ZStack {
            if #available(iOS 26, *) {
                Color.clear
            } else {
                Color(.systemGroupedBackground).ignoresSafeArea()
            }

            VStack(spacing: 0) {
                // Status filter chips
                ScrollView(.horizontal, showsIndicators: false) {
                    HStack(spacing: 10) {
                        ForEach(statusOptions, id: \.label) { option in
                            Button(option.label) {
                                selectedStatus = option.value
                                viewModel.fetchOrders(status: selectedStatus, isFirstLoad: true)
                            }
                            .font(.subheadline)
                            .padding(.horizontal, 16)
                            .padding(.vertical, 8)
                            .background(
                                selectedStatus == option.value
                                    ? Color.accentColor
                                    : Color(.secondarySystemBackground)
                            )
                            .foregroundColor(
                                selectedStatus == option.value ? .white : .primary
                            )
                            .clipShape(Capsule())
                        }
                    }
                    .padding(.horizontal, 16)
                    .padding(.vertical, 12)
                }

                // Content
                Group {
                    if let _ = state as? OrderListState.Loading {
                        ProgressView()
                            .frame(maxWidth: .infinity, maxHeight: .infinity)
                    } else if let success = state as? OrderListState.Success {
                        if success.data.isEmpty {
                            emptyView
                        } else {
                            ScrollView {
                                LazyVStack(spacing: 12) {
                                    ForEach(success.data, id: \.id) { order in
                                        OrderCard(order: order)
                                            .onTapGesture {
                                                if let id = order.id {
                                                    rootView = .order_detail(id)
                                                }
                                            }
                                            .onAppear {
                                                loadMoreIfNeeded(order: order, success: success)
                                            }
                                    }
                                    if success.isLoadingMore {
                                        ProgressView().padding()
                                    }
                                }
                                .padding(16)
                            }
                        }
                    } else if let error = state as? OrderListState.Error {
                        VStack(spacing: 12) {
                            Image(systemName: "exclamationmark.circle")
                                .font(.system(size: 44))
                                .foregroundColor(.red)
                            Text(error.message)
                                .multilineTextAlignment(.center)
                            Button("Retry") {
                                viewModel.fetchOrders(status: selectedStatus, isFirstLoad: true)
                            }
                            .buttonStyle(.borderedProminent)
                        }
                        .padding()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                    } else {
                        Color.clear
                    }
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
            }
        }
        .navigationTitle("My Orders")
        .navigationBarTitleDisplayMode(.large)
        .onAppear {
            viewModel.fetchOrders(status: selectedStatus, isFirstLoad: true)
            observeState()
        }
    }

    private var emptyView: some View {
        VStack(spacing: 16) {
            Image(systemName: "bag")
                .font(.system(size: 60))
                .foregroundColor(.secondary)
            Text("No orders yet")
                .font(.headline)
            Text("Place your first order and it'll show up here.")
                .font(.subheadline)
                .foregroundColor(.secondary)
                .multilineTextAlignment(.center)
        }
        .padding()
        .frame(maxWidth: .infinity, maxHeight: .infinity)
    }

    private func loadMoreIfNeeded(order: OrderEntity, success: OrderListState.Success) {
        let data = success.data
        guard let index = data.firstIndex(where: { $0.id == order.id }),
              index >= data.count - 2,
              !success.isLastPage,
              !success.isLoadingMore
        else { return }
        viewModel.fetchOrders(status: selectedStatus, isFirstLoad: false)
    }

    private func observeState() {
        viewModel.orderListState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState { self.state = s }
            }
        }
    }
}

// MARK: - OrderCard

struct OrderCard: View {
    let order: OrderEntity

    var body: some View {
        VStack(alignment: .leading, spacing: 10) {
            HStack {
                Text("Order #\(shortId(order.id))")
                    .font(.headline)
                Spacer()
                StatusBadge(status: order.status ?? "")
            }

            Divider()

            if let items = order.items, !items.isEmpty {
                ForEach(items.prefix(2), id: \.id) { item in
                    HStack {
                        Text(item.productName ?? "Product")
                            .font(.subheadline)
                            .lineLimit(1)
                        Spacer()
                        Text("×\(item.quantity ?? 0)")
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                }
                if (order.items?.count ?? 0) > 2 {
                    Text("+\((order.items?.count ?? 0) - 2) more items")
                        .font(.caption)
                        .foregroundColor(.secondary)
                }
            }

            Divider()

            HStack {
                Text("Total")
                    .font(.subheadline)
                    .foregroundColor(.secondary)
                Spacer()
                Text(order.localizedTotalPrice ?? "-")
                    .font(.headline)
                    .foregroundColor(.accentColor)
            }
        }
        .padding(16)
        .glassCard(cornerRadius: 14)
    }

    private func shortId(_ id: String?) -> String {
        guard let id = id, id.count >= 8 else { return id ?? "-" }
        return String(id.suffix(8)).uppercased()
    }
}

// MARK: - StatusBadge

struct StatusBadge: View {
    let status: String

    private var color: Color {
        switch status {
        case "pending": return .orange
        case "completed": return .green
        case "canceled": return .red
        default: return .secondary
        }
    }

    var body: some View {
        Text(status.capitalized)
            .font(.caption)
            .fontWeight(.semibold)
            .padding(.horizontal, 10)
            .padding(.vertical, 4)
            .background(color.opacity(0.15))
            .foregroundColor(color)
            .clipShape(Capsule())
    }
}
