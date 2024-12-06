package com.luckyfrog.quickmart.core.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.getBooleanFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class BooleanSettingConfig(settings: Settings, key: String, defaultValue: Boolean) :
    SettingConfigBoolean<Boolean>(settings, key, defaultValue) {

    @OptIn(ExperimentalSettingsApi::class)
    override val value: Flow<Boolean>
        get() {
            val observableSettings = settings as? ObservableSettings ?: return emptyFlow()
            return observableSettings.getBooleanFlow(key, defaultValue)
        }

    override fun setBooleanValue(settings: Settings, key: String, value: Boolean) {
        settings.putBoolean(key, value)
    }

    override fun getBooleanValue(settings: Settings, key: String, defaultValue: Boolean): Boolean {
        return settings.getBoolean(key, defaultValue)
    }

}