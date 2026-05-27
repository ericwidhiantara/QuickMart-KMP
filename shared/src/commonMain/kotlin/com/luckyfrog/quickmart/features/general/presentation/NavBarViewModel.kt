package com.luckyfrog.quickmart.features.general.presentation

import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavBarViewModel : ViewModel() {
    // Hold the index of the currently selected navigation item
    private val _state = MutableStateFlow(0)
    var currentIndex = _state.asStateFlow().cStateFlow()
        private set

    // Function to update the currentIndex
    fun updateIndex(index: Int) {
        println("updateIndex: $index")
        _state.value = index
    }
}
