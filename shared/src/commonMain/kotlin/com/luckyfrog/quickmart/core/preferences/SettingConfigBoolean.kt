package com.luckyfrog.quickmart.core.preferences

import com.russhwolf.settings.Settings
import kotlinx.coroutines.flow.Flow

sealed class SettingConfigBoolean<T>(
    protected val settings: Settings,
    val key: String,
    protected val defaultValue: T,
) {

    protected abstract fun getBooleanValue(
        settings: Settings,
        key: String,
        defaultValue: T
    ): Boolean

    protected abstract fun setBooleanValue(settings: Settings, key: String, value: Boolean)

    fun remove() = settings.remove(key)
    fun exists(): Boolean = settings.hasKey(key)

    fun get(): Boolean = getBooleanValue(settings, key, defaultValue)
    fun set(value: Boolean): Boolean {
        return try {
            setBooleanValue(settings, key, value)
            true
        } catch (exception: Exception) {
            false
        }
    }

    override fun toString() = key

    abstract val value: Flow<Boolean>
}