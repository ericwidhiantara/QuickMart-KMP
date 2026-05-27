package com.luckyfrog.quickmart.features.review.presentation.product_review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewEntity
import com.luckyfrog.quickmart.features.review.domain.entities.ReviewSummaryEntity
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewSection(
    productId: String,
    currentUserId: String? = null,
    viewModel: ProductReviewViewModel = koinViewModel()
) {
    val listState by viewModel.reviewListState.collectAsState()
    val createState by viewModel.createReviewState.collectAsState()
    val deleteState by viewModel.deleteReviewState.collectAsState()

    var showReviewSheet by remember { mutableStateOf(false) }
    var selectedRating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(productId) {
        viewModel.fetchReviews(productId = productId)
    }

    // Refresh after successful create/delete
    LaunchedEffect(createState) {
        if (createState is CreateReviewState.Success) {
            showReviewSheet = false
            comment = ""
            selectedRating = 5
            viewModel.resetCreateState()
            viewModel.fetchReviews(productId = productId)
        }
    }
    LaunchedEffect(deleteState) {
        if (deleteState is DeleteReviewState.Success) {
            viewModel.resetDeleteState()
            viewModel.fetchReviews(productId = productId)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Reviews", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            TextButton(onClick = { showReviewSheet = true }) {
                Text("Write Review")
            }
        }

        when (val s = listState) {
            is ReviewListState.Loading -> {
                Box(Modifier.fillMaxWidth().height(80.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ReviewListState.Success -> {
                ReviewSummaryHeader(summary = s.summary)
                Spacer(Modifier.height(8.dp))
                if (s.summary.reviews.isEmpty()) {
                    Text(
                        "No reviews yet. Be the first!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    s.summary.reviews.take(3).forEach { review ->
                        ReviewRow(
                            review = review,
                            isOwn = review.userId == currentUserId,
                            onDelete = { review.id?.let { viewModel.deleteReview(it) } }
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
            is ReviewListState.Error -> {
                Text(s.message, color = MaterialTheme.colorScheme.error)
            }
            else -> {}
        }
    }

    // Write review sheet
    if (showReviewSheet) {
        ModalBottomSheet(
            onDismissRequest = { showReviewSheet = false },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Write a Review", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

                // Star selector
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    (1..5).forEach { star ->
                        IconButton(onClick = { selectedRating = star }) {
                            Icon(
                                Icons.Filled.Star,
                                contentDescription = null,
                                tint = if (star <= selectedRating) Color(0xFFFFA000) else Color(0xFFCCCCCC),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text("Comment (optional)") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    maxLines = 5
                )

                Button(
                    onClick = {
                        viewModel.createReview(
                            productId = productId,
                            rating = selectedRating,
                            comment = comment.takeIf { it.isNotBlank() }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = createState !is CreateReviewState.Loading,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (createState is CreateReviewState.Loading) {
                        CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                    } else {
                        Text("Submit Review")
                    }
                }
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ReviewSummaryHeader(summary: ReviewSummaryEntity) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = String.format("%.1f", summary.averageRating),
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.Bold
        )
        Column {
            StarRow(rating = summary.averageRating)
            Text(
                text = "${summary.totalReviews} reviews",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ReviewRow(review: ReviewEntity, isOwn: Boolean, onDelete: () -> Unit) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Review") },
            text = { Text("Are you sure you want to delete this review?") },
            confirmButton = {
                TextButton(onClick = { showDeleteDialog = false; onDelete() }) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) { Text("Cancel") }
            }
        )
    }

    Card(
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text(
                        review.reviewerName ?: "Anonymous",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    StarRow(rating = (review.rating ?: 0).toDouble())
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (isOwn) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Delete", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                    Text(
                        review.createdAt?.take(10) ?: "",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            review.comment?.takeIf { it.isNotBlank() }?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

@Composable
fun StarRow(rating: Double) {
    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
        (1..5).forEach { star ->
            Icon(
                Icons.Filled.Star,
                contentDescription = null,
                tint = if (star.toDouble() <= rating) Color(0xFFFFA000) else Color(0xFFCCCCCC),
                modifier = Modifier.size(14.dp)
            )
        }
    }
}
