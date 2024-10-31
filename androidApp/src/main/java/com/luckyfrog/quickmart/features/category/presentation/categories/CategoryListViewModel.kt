package com.luckyfrog.quickmart.features.category.presentation.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.features.category.domain.entities.CategoryEntity
import com.luckyfrog.quickmart.features.category.domain.usecases.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryListViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _data = MutableStateFlow<List<CategoryEntity>?>(null)
    val data: StateFlow<List<CategoryEntity>?> = _data

    fun fetchCategories() {
        viewModelScope.launch {
            try {
                val result = getCategoryUseCase.execute(Unit)
                _data.value = result
            } catch (e: Exception) {
                // Handle the error, e.g., show a message or log the error
                _data.value = null
            }
        }
    }
}
