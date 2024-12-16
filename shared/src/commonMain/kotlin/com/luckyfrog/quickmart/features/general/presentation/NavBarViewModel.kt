package com.luckyfrog.quickmart.features.general.presentation


import androidx.compose.runtime.mutableIntStateOf
import dev.icerock.moko.mvvm.viewmodel.ViewModel

class NavBarViewModel : ViewModel() {

    // Hold the index of the currently selected navigation item
    var currentIndex = mutableIntStateOf(0)
        private set

    // Function to update the currentIndex
    fun updateIndex(index: Int) {
        currentIndex.intValue = index
    }
}
