package com.luckyfrog.quickmart.core.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.luckyfrog.quickmart.core.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class QuickMart : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@QuickMart)
            modules(getSharedModules())
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
        }
    }

    private fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

}