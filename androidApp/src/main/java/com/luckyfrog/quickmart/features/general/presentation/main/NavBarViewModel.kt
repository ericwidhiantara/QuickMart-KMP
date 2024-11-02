package com.luckyfrog.quickmart.features.general.presentation.main


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavBarViewModel @Inject constructor() : ViewModel() {

    // Hold the index of the currently selected navigation item
    var currentIndex = mutableStateOf(0)
        private set

    // Function to update the currentIndex
    fun updateIndex(index: Int) {
        currentIndex.value = index
    }
}
