package com.luckyfrog.quickmart.features.shipping_address.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.repositories.ShippingAddressRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class DeleteShippingAddressUseCase(
    private val repository: ShippingAddressRepository
) : UseCase<String, Flow<ApiResponse<ResponseDto<Unit>>>> {
    override suspend fun execute(input: String): Flow<ApiResponse<ResponseDto<Unit>>> {
        return repository.deleteAddress(input)
    }
}
