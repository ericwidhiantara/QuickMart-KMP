package com.luckyfrog.quickmart.features.product.domain.repositories

import androidx.paging.PagingData
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Flow<PagingData<ProductEntity>>

    suspend fun getProductsByCategory(category: String): Flow<PagingData<ProductEntity>>

    suspend fun getProductDetail(id: Int): ProductEntity
}