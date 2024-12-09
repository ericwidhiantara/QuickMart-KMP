package com.luckyfrog.quickmart.core.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.luckyfrog.quickmart.core.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.module


class QuickMart : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            modules(
                module {
                    single { this@QuickMart } // Provide the application context
                },
            )
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@QuickMart) // Inject Android context
        }
    }

    private fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

}