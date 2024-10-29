package com.luckyfrog.quickmart.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import com.luckyfrog.ecommerce.core.app.MainActivity
import java.util.Locale

object LocalizationUtils {
    fun setLocale(context: Context, localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)

        // Create a new configuration with the updated locale
        val config = Configuration(context.resources.configuration)
        config.setLocale(locale)

        // Create a new context with the updated configuration
        val localizedContext = context.createConfigurationContext(config)

        // Restart the activity to apply the changes
        context.startActivity(Intent(localizedContext, MainActivity::class.java))
        (context as Activity).finish()
    }
}