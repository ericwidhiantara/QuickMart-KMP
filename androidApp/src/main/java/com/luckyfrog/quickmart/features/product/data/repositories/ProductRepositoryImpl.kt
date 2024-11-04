package com.luckyfrog.quickmart.features.product.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.luckyfrog.quickmart.features.product.data.datasources.remote.ProductRemoteDataSource
import com.luckyfrog.quickmart.features.product.data.models.mapper.toEntity
import com.luckyfrog.quickmart.features.product.data.repositories.paging.ProductByCategoryPagingSource
import com.luckyfrog.quickmart.features.product.data.repositories.paging.ProductPagingSource
import com.luckyfrog.quickmart.features.product.domain.entities.ProductEntity
import com.luckyfrog.quickmart.features.product.domain.entities.ProductFormParamsEntity
import com.luckyfrog.quickmart.features.product.domain.repositories.ProductRepository
import com.luckyfrog.quickmart.utils.helper.Constants
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductRemoteDataSource
) : ProductRepository {

    override suspend fun getProducts(): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductPagingSource(remoteDataSource)
            }
        ).flow
    }

    override suspend fun getProductsByCategory(params: ProductFormParamsEntity): Flow<PagingData<ProductEntity>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.MAX_PAGE_SIZE, prefetchDistance = 2),
            pagingSourceFactory = {
                ProductByCategoryPagingSource(remoteDataSource, params)
            }
        ).flow
    }

    override suspend fun getProductDetail(id: Int): ProductEntity {
        return remoteDataSource.getProductDetail(id = id).toEntity()
    }
}