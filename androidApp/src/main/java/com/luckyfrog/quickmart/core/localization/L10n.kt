package com.luckyfrog.quickmart.core.localization

import android.content.Context
import androidx.annotation.StringRes
import java.util.Locale

object L10n {
    val all: List<Locale> = listOf(
        Locale("en"),
        Locale("id")
    )

    fun getFlag(code: String): String {
        return when (code) {
            "id" -> "Bahasa"
            "en" -> "English"
            else -> "English" // Default case
        }
    }

    // Function to get a localized string based on resource ID
    fun getString(context: Context, @StringRes resId: Int): String {
        return context.getString(resId)
    }
}
