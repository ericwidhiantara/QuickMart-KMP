package com.luckyfrog.quickmart.core.app

import android.app.Application
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.luckyfrog.quickmart.utils.resource.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var stateApp by mutableStateOf(MainState())

    // Handles events like theme changes
    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ThemeChange -> {
                stateApp = stateApp.copy(theme = event.theme)
                saveTheme(event.theme)
            }
        }
    }

    // Load the theme from AppPreferences
    fun loadTheme(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val theme = AppPreferences.getTheme(context)
            stateApp = stateApp.copy(theme = theme)
        }
    }

    // Save the theme to AppPreferences
    private fun saveTheme(theme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            AppPreferences.setTheme(theme, getApplication())
        }
    }
}

// Event class for handling various UI events
sealed class MainEvent {
    data class ThemeChange(val theme: AppTheme) : MainEvent()
}

// Data class for holding app state
data class MainState(
    val theme: AppTheme = AppTheme.Default // Default theme
)
