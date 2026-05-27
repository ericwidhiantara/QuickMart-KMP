//
//  OrderDetailView.swift
//  iosApp
//

import Shared
import SwiftUI

struct OrderDetailView: View {
    @Binding var rootView: AppScreen
    let orderId: String

    @ObservedObject var viewModel: OrderDetailViewModel = KoinHelper().getOrderDetailViewModel()
    @State private var detailState: OrderDetailState = OrderDetailState.Idle()
    @State private var cancelState: CancelOrderState = CancelOrderState.Idle()
    @State private var showCancelConfirm = false
    @State private var showCancelError = false
    @State private var cancelErrorMsg = ""

    var body: some View {
        ZStack {
            if #available(iOS 26, *) {
                Color.clear
            } else {
                Color(.systemGroupedBackground).ignoresSafeArea()
            }

            Group {
                if let _ = detailState as? OrderDetailState.Loading {
                    ProgressView()
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else if let success = detailState as? OrderDetailState.Success {
                    orderContent(order: success.order)
                } else if let error = detailState as? OrderDetailState.Error {
                    VStack(spacing: 12) {
                        Image(systemName: "exclamationmark.circle")
                            .font(.system(size: 44))
                            .foregroundColor(.red)
                        Text(error.message)
                        Button("Retry") { viewModel.fetchOrderDetail(id: orderId) }
                            .buttonStyle(.borderedProminent)
                    }
                    .padding()
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else {
                    Color.clear
                }
            }
        }
        .navigationTitle("Order Detail")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button(action: { rootView = .order_list }) {
                    Image(systemName: "chevron.left")
                }
            }
        }
        .confirmationDialog("Cancel Order", isPresented: $showCancelConfirm, titleVisibility: .visible) {
            Button("Cancel Order", role: .destructive) {
                viewModel.cancelOrder(id: orderId)
            }
            Button("Keep Order", role: .cancel) {}
        } message: {
            Text("This order will be canceled and cannot be undone.")
        }
        .alert("Error", isPresented: $showCancelError) {
            Button("OK", role: .cancel) {}
        } message: {
            Text(cancelErrorMsg)
        }
        .onAppear {
            viewModel.fetchOrderDetail(id: orderId)
            observeStates()
        }
    }

    @ViewBuilder
    private func orderContent(order: OrderEntity) -> some View {
        ScrollView {
            VStack(spacing: 16) {
                // Header card
                VStack(spacing: 12) {
                    HStack {
                        VStack(alignment: .leading, spacing: 4) {
                            Text("Order #\(shortId(order.id))")
                                .font(.headline)
                            Text(formatDate(order.createdAt))
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        Spacer()
                        StatusBadge(status: order.status ?? "")
                    }
                }
                .padding(16)
                .glassCard(cornerRadius: 14)

                // Items
                VStack(alignment: .leading, spacing: 12) {
                    Text("Items")
                        .font(.headline)
                        .padding(.horizontal, 4)

                    if let items = order.items {
                        ForEach(items, id: \.id) { item in
                            OrderItemRow(item: item)
                            if item.id != items.last?.id {
                                Divider()
                            }
                        }
                    }
                }
                .padding(16)
                .glassCard(cornerRadius: 14)

                // Summary
                VStack(spacing: 10) {
                    HStack {
                        Text("Total")
                            .font(.headline)
                        Spacer()
                        Text(order.localizedTotalPrice ?? "-")
                            .font(.title3)
                            .fontWeight(.bold)
                            .foregroundColor(.accentColor)
                    }
                }
                .padding(16)
                .glassCard(cornerRadius: 14)

                // Cancel button — only for pending
                if order.status == "pending" {
                    Button(action: { showCancelConfirm = true }) {
                        HStack {
                            if let _ = cancelState as? CancelOrderState.Loading {
                                ProgressView().tint(.white)
                            } else {
                                Image(systemName: "xmark.circle")
                                Text("Cancel Order")
                            }
                        }
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(12)
                    }
                    .disabled(cancelState is CancelOrderState.Loading)
                }
            }
            .padding(16)
        }
    }

    private func shortId(_ id: String?) -> String {
        guard let id = id, id.count >= 8 else { return id ?? "-" }
        return String(id.suffix(8)).uppercased()
    }

    private func formatDate(_ dateStr: String?) -> String {
        guard let dateStr = dateStr else { return "-" }
        let formatter = ISO8601DateFormatter()
        formatter.formatOptions = [.withInternetDateTime, .withFractionalSeconds]
        if let date = formatter.date(from: dateStr) {
            let display = DateFormatter()
            display.dateStyle = .medium
            display.timeStyle = .short
            return display.string(from: date)
        }
        return dateStr
    }

    private func observeStates() {
        viewModel.detailState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState { self.detailState = s }
            }
        }
        viewModel.cancelState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState {
                    self.cancelState = s
                    if let err = s as? CancelOrderState.Error {
                        self.cancelErrorMsg = err.message
                        self.showCancelError = true
                    }
                }
            }
        }
    }
}

// MARK: - OrderItemRow

struct OrderItemRow: View {
    let item: OrderItemEntity

    var body: some View {
        HStack(spacing: 12) {
            VStack(alignment: .leading, spacing: 4) {
                Text(item.productName ?? "Product")
                    .font(.subheadline)
                    .fontWeight(.medium)
                    .lineLimit(2)
                if let discount = item.discountPercentage, discount > 0 {
                    Text("\(Int(discount))% off")
                        .font(.caption)
                        .foregroundColor(.green)
                }
            }
            Spacer()
            VStack(alignment: .trailing, spacing: 4) {
                Text("×\(item.quantity ?? 0)")
                    .font(.caption)
                    .foregroundColor(.secondary)
                if let price = item.finalPrice {
                    Text(String(format: "Rp %.0f", price))
                        .font(.subheadline)
                        .fontWeight(.semibold)
                }
            }
        }
        .padding(.vertical, 4)
    }
}
