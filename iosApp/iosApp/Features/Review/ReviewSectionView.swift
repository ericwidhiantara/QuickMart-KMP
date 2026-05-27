//
//  ReviewSectionView.swift
//  iosApp
//

import Shared
import SwiftUI

/// Embeddable review section — used inside ProductDetailView
struct ReviewSectionView: View {
    let productId: String
    let currentUserId: String? // to show delete button on own reviews

    @ObservedObject var viewModel: ProductReviewViewModel = KoinHelper().getProductReviewViewModel()
    @State private var state: ReviewListState = ReviewListState.Idle()
    @State private var createState: CreateReviewState = CreateReviewState.Idle()
    @State private var deleteState: DeleteReviewState = DeleteReviewState.Idle()

    // Write review form
    @State private var showReviewForm = false
    @State private var selectedRating: Int = 5
    @State private var comment: String = ""

    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            // Header
            HStack {
                Text("Reviews")
                    .font(.headline)
                Spacer()
                Button("Write Review") {
                    showReviewForm = true
                }
                .font(.subheadline)
                .foregroundColor(.accentColor)
            }

            // Summary
            if let success = state as? ReviewListState.Success {
                reviewSummaryHeader(summary: success.summary)
            }

            // Loading
            if let _ = state as? ReviewListState.Loading {
                ProgressView()
                    .frame(maxWidth: .infinity)
            }

            // Reviews list
            if let success = state as? ReviewListState.Success {
                if success.summary.reviews.isEmpty {
                    Text("No reviews yet. Be the first!")
                        .font(.subheadline)
                        .foregroundColor(.secondary)
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding()
                } else {
                    ForEach(success.summary.reviews.prefix(3), id: \.id) { review in
                        ReviewRowView(
                            review: review,
                            isOwn: review.userId == currentUserId,
                            onDelete: {
                                if let id = review.id {
                                    viewModel.deleteReview(reviewId: id)
                                }
                            }
                        )
                    }
                }
            }
        }
        .sheet(isPresented: $showReviewForm) {
            WriteReviewSheet(
                rating: $selectedRating,
                comment: $comment,
                isLoading: createState is CreateReviewState.Loading,
                onSubmit: {
                    viewModel.createReview(
                        productId: productId,
                        rating: Int32(selectedRating),
                        comment: comment.isEmpty ? nil : comment
                    )
                },
                onDismiss: { showReviewForm = false }
            )
            .presentationDetents([.medium])
        }
        .onAppear {
            viewModel.fetchReviews(
                productId: productId,
                rating: nil,
                sortBy: "created_at",
                sortOrder: "desc",
                page: 1,
                limit: 10
            )
            observeStates()
        }
    }

    @ViewBuilder
    private func reviewSummaryHeader(summary: ReviewSummaryEntity) -> some View {
        HStack(spacing: 12) {
            VStack(spacing: 4) {
                Text(String(format: "%.1f", summary.averageRating))
                    .font(.system(size: 40, weight: .bold))
                StarRatingView(rating: summary.averageRating)
                Text("\(summary.totalReviews) reviews")
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            Spacer()
        }
    }

    private func observeStates() {
        viewModel.reviewListState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState { self.state = s }
            }
        }
        viewModel.createReviewState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState {
                    self.createState = s
                    if s is CreateReviewState.Success {
                        self.showReviewForm = false
                        self.comment = ""
                        self.selectedRating = 5
                        viewModel.resetCreateState()
                        // Refresh
                        viewModel.fetchReviews(
                            productId: productId,
                            rating: nil,
                            sortBy: "created_at",
                            sortOrder: "desc",
                            page: 1,
                            limit: 10
                        )
                    }
                }
            }
        }
        viewModel.deleteReviewState.subscribe { newState in
            DispatchQueue.main.async {
                if let s = newState {
                    self.deleteState = s
                    if s is DeleteReviewState.Success {
                        viewModel.resetDeleteState()
                        viewModel.fetchReviews(
                            productId: productId,
                            rating: nil,
                            sortBy: "created_at",
                            sortOrder: "desc",
                            page: 1,
                            limit: 10
                        )
                    }
                }
            }
        }
    }
}

// MARK: - ReviewRowView

struct ReviewRowView: View {
    let review: ReviewEntity
    let isOwn: Bool
    let onDelete: () -> Void
    @State private var showDeleteConfirm = false

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            HStack {
                VStack(alignment: .leading, spacing: 2) {
                    Text(review.reviewerName ?? "Anonymous")
                        .font(.subheadline)
                        .fontWeight(.semibold)
                    StarRatingView(rating: Double(review.rating ?? 0))
                }
                Spacer()
                if isOwn {
                    Button(action: { showDeleteConfirm = true }) {
                        Image(systemName: "trash")
                            .foregroundColor(.red)
                            .font(.subheadline)
                    }
                }
                Text(formatDate(review.createdAt))
                    .font(.caption)
                    .foregroundColor(.secondary)
            }

            if let comment = review.comment, !comment.isEmpty {
                Text(comment)
                    .font(.subheadline)
                    .foregroundColor(.primary)
            }
        }
        .padding(12)
        .glassCard(cornerRadius: 10)
        .confirmationDialog("Delete Review", isPresented: $showDeleteConfirm, titleVisibility: .visible) {
            Button("Delete", role: .destructive) { onDelete() }
            Button("Cancel", role: .cancel) {}
        }
    }

    private func formatDate(_ str: String?) -> String {
        guard let str = str else { return "" }
        return String(str.prefix(10))
    }
}

// MARK: - StarRatingView

struct StarRatingView: View {
    let rating: Double

    var body: some View {
        HStack(spacing: 2) {
            ForEach(1...5, id: \.self) { star in
                Image(systemName: starIcon(for: star))
                    .foregroundColor(.orange)
                    .font(.system(size: 12))
            }
        }
    }

    private func starIcon(for star: Int) -> String {
        if Double(star) <= rating { return "star.fill" }
        if Double(star) - 0.5 <= rating { return "star.leadinghalf.filled" }
        return "star"
    }
}

// MARK: - WriteReviewSheet

struct WriteReviewSheet: View {
    @Binding var rating: Int
    @Binding var comment: String
    let isLoading: Bool
    let onSubmit: () -> Void
    let onDismiss: () -> Void

    var body: some View {
        NavigationStack {
            VStack(spacing: 20) {
                // Star picker
                Text("Rate this product")
                    .font(.headline)

                HStack(spacing: 8) {
                    ForEach(1...5, id: \.self) { star in
                        Button(action: { rating = star }) {
                            Image(systemName: star <= rating ? "star.fill" : "star")
                                .foregroundColor(.orange)
                                .font(.system(size: 32))
                        }
                    }
                }

                // Comment
                TextEditor(text: $comment)
                    .frame(height: 100)
                    .padding(8)
                    .background(Color(.secondarySystemBackground))
                    .cornerRadius(8)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(Color(.separator), lineWidth: 1)
                    )
                    .overlay(
                        Group {
                            if comment.isEmpty {
                                Text("Leave a comment (optional)")
                                    .foregroundColor(.secondary)
                                    .padding(.leading, 12)
                                    .padding(.top, 14)
                                    .allowsHitTesting(false)
                            }
                        },
                        alignment: .topLeading
                    )

                Button(action: onSubmit) {
                    if isLoading {
                        ProgressView().tint(.white)
                    } else {
                        Text("Submit Review")
                    }
                }
                .frame(maxWidth: .infinity)
                .padding()
                .background(Color.accentColor)
                .foregroundColor(.white)
                .cornerRadius(12)
                .disabled(isLoading)

                Spacer()
            }
            .padding(20)
            .navigationTitle("Write a Review")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") { onDismiss() }
                }
            }
        }
    }
}
