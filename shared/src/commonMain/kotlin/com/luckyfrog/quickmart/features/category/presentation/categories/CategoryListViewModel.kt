package com.luckyfrog.quickmart.features.category.presentation.categories

import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryFormParamsEntity
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import com.luckyfrog.quickmart.utils.ApiResponse
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

sealed class CategoryState {
    data object Idle : CategoryState()
    data class Success(
        val data: List<CategoryEntity>,
        val isLastPage: Boolean,
        val isLoadingMore: Boolean = false
    ) : CategoryState()

    data object LoadingFirstPage : CategoryState()
    data class Error(val message: String) : CategoryState()
}

class CategoryListViewModel(
    private val _usecase: GetCategoriesUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<CategoryState>(CategoryState.Idle)
    val state = _state.asStateFlow().cStateFlow()

    private var currentPage = 1
    private val productList = mutableListOf<CategoryEntity>()
    private var isLastPage = false
    private var isLoadingMore = false  // Track if we're loading more items
    private var itemKeys = mutableMapOf<Int, Long>()  // Store index to timestamp mapping

    fun fetchCategories(params: CategoryFormParamsEntity, isFirstLoad: Boolean = false) {
        if (isFirstLoad) {
            currentPage = 1
            productList.clear()
            _state.value = CategoryState.LoadingFirstPage
        } else {
            if (isLastPage || isLoadingMore) return
            isLoadingMore = true
            // Update current success state to show loading more
            (_state.value as? CategoryState.Success)?.let { currentState ->
                _state.value = currentState.copy(isLoadingMore = true)
            }
        }

        viewModelScope.launch {
            val paginatedParams = params.copy(page = currentPage)
            _usecase.execute(paginatedParams).collect { response ->
                when (response) {
                    is ApiResponse.Success -> {
                        val newData = response.data.data?.data ?: emptyList()
                        productList.addAll(newData)

                        isLastPage = newData.isEmpty() ||
                                (response.data.data?.data?.size ?: 0) < params.limit

                        _state.value = CategoryState.Success(
                            data = productList.toList(),
                            isLastPage = isLastPage,
                            isLoadingMore = false
                        )

                        if (!isLastPage) {
                            currentPage++
                        }
                        isLoadingMore = false
                    }

                    is ApiResponse.Failure -> {
                        if (isFirstLoad) {
                            _state.value = CategoryState.Error(response.errorMessage)
                        } else {
                            // If pagination fails, revert to previous success state without loading
                            (_state.value as? CategoryState.Success)?.let { currentState ->
                                _state.value = currentState.copy(isLoadingMore = false)
                            }
                        }
                        isLoadingMore = false
                    }

                    else -> {} // Handle other cases if needed
                }
            }
        }
    }

    fun getKeyForIndex(index: Int): Long {
        return itemKeys[index] ?: run {
            val newKey = Clock.System.now().toEpochMilliseconds() + index
            itemKeys[index] = newKey
            newKey
        }
    }
}