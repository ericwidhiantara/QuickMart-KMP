package com.luckyfrog.quickmart.features.product.presentation.product_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.luckyfrog.quickmart.core.widgets.CustomTopBar
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity

@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    productId: Int, // Pass productId to fetch the details
    navController: NavController,
) {

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Product Detail",
                navController = navController
            )
        },
        content = { paddingValues ->
            // Trigger the product detail fetch
            LaunchedEffect(productId) {
                viewModel.fetchProductDetail(productId)
            }

            // Observe the product details from the ViewModel
            val productDetail by viewModel.productDetail.collectAsState()

            // Display the product details
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {

                when {
                    productDetail == null -> {
                        // Loading or error state
                        Text(
                            text = "Loading product details...",
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    else -> {
                        // Product is loaded, show its details
                        productDetail?.let { product ->
                            ProductDetailContent(product = product)
                        }
                    }
                }
            }
        }
    )

}

@Composable
fun ProductDetailContent(product: ProductEntity) {
    Column {
        Text(
            text = "Product Name: ${product.title}",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(text = "Product Price: ${product.price}", style = MaterialTheme.typography.bodyLarge)
        Text(
            text = "Product Description: ${product.description}",
            style = MaterialTheme.typography.bodySmall
        )
        // Add more fields as needed
    }
}
