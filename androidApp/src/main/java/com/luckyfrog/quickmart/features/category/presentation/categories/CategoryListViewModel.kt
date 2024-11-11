package com.luckyfrog.quickmart.features.category.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.core.generic.entities.PaginationEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import com.luckyfrog.quickmart.utils.helper.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CategoryState {
    data object Idle : CategoryState()
    data object Loading : CategoryState()
    data class Success(val data: PaginationEntity<CategoryEntity>) : CategoryState()
    data class Error(val message: String) : CategoryState()
}

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val _usecase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CategoryState>(CategoryState.Idle)
    val state: StateFlow<CategoryState> = _state

    fun fetchCategories() {
        viewModelScope.launch {
            _usecase.execute(Unit).collect { response ->
                when (response) {
                    is ApiResponse.Loading -> {
                        _state.value = CategoryState.Loading
                    }

                    is ApiResponse.Success -> {
                        _state.value = CategoryState.Success(response.data)
                    }

                    is ApiResponse.Failure -> {
                        _state.value = CategoryState.Error(response.errorMessage)
                    }
                }
            }
        }
    }
}