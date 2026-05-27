//
//  WalletView.swift
//  iosApp
//

import Shared
import SwiftUI

struct WalletView: View {
    @Binding var rootView: AppScreen
    @ObservedObject var viewModel: WalletViewModel = KoinHelper().getWalletViewModel()

    @State private var walletState: WalletState = WalletState.Idle()
    @State private var topUpState: TopUpState = TopUpState.Idle()
    @State private var amount: String = ""
    @State private var showSuccess = false

    var body: some View {
        Form {
            // Balance card
            Section {
                VStack(spacing: 8) {
                    Text("Current Balance")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                    if let s = walletState as? WalletState.Success {
                        Text(s.wallet.localizedBalance)
                            .font(.system(size: 34, weight: .bold))
                            .foregroundColor(.colorCyan)
                    } else if walletState is WalletState.Loading {
                        ProgressView()
                    } else if let e = walletState as? WalletState.Error {
                        Text(e.message).foregroundColor(.red)
                    } else {
                        Text("—").font(.system(size: 34, weight: .bold))
                    }
                }
                .frame(maxWidth: .infinity)
                .padding(.vertical, 8)
            }

            // Top up
            Section("Top Up") {
                HStack {
                    Text("Rp")
                        .foregroundColor(.secondary)
                    TextField("0", text: $amount)
                        .keyboardType(.decimalPad)
                }

                let isLoading = topUpState is TopUpState.Loading
                let amountValue = Double(amount) ?? 0
                Button(action: {
                    if amountValue > 0 { viewModel.topUp(amount: amountValue) }
                }) {
                    if isLoading {
                        HStack { ProgressView(); Text("Processing...") }
                    } else {
                        Text("Top Up").frame(maxWidth: .infinity).fontWeight(.semibold)
                    }
                }
                .disabled(isLoading || amountValue <= 0)
            }
        }
        .navigationTitle("My Wallet")
        .navigationBarTitleDisplayMode(.large)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button("Back") { rootView = .main }
            }
        }
        .onAppear {
            viewModel.fetchWallet()
            observeStates()
        }
        .alert("Top-up successful!", isPresented: $showSuccess) {
            Button("OK") {}
        }
    }

    private func observeStates() {
        viewModel.walletState.subscribe { s in
            DispatchQueue.main.async { if let s = s { self.walletState = s } }
        }
        viewModel.topUpState.subscribe { s in
            DispatchQueue.main.async {
                if let s = s {
                    self.topUpState = s
                    if s is TopUpState.Success {
                        self.showSuccess = true
                        self.amount = ""
                        viewModel.resetTopUpState()
                        viewModel.fetchWallet()
                    }
                }
            }
        }
    }
}
