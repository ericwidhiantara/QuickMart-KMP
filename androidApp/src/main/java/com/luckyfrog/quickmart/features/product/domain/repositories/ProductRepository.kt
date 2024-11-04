package com.luckyfrog.quickmart.features.product.domain.repositories

import androidx.paging.PagingData
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Flow<PagingData<ProductEntity>>

    suspend fun getProductsByCategory(params: ProductFormParamsEntity): Flow<PagingData<ProductEntity>>

    suspend fun getProductDetail(id: Int): ProductEntity
}