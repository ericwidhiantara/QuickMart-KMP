package com.luckyfrog.quickmart.features.shipping_address.domain.usecases

import com.luckyfrog.quickmart.core.generic.dto.ResponseDto
import com.luckyfrog.quickmart.core.generic.usecase.UseCase
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.ShippingAddressEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.entities.UpdateShippingAddressParamsEntity
import com.luckyfrog.quickmart.features.shipping_address.domain.repositories.ShippingAddressRepository
import com.luckyfrog.quickmart.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class UpdateShippingAddressUseCase(
    private val repository: ShippingAddressRepository
) : UseCase<UpdateShippingAddressParamsEntity, Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>>> {
    override suspend fun execute(input: UpdateShippingAddressParamsEntity): Flow<ApiResponse<ResponseDto<ShippingAddressEntity>>> {
        return repository.updateAddress(input)
    }
}
