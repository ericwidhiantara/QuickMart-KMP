package com.luckyfrog.quickmart.core.app

import android.app.Application
import android.content.Context
import android.content.pm.ApplicationInfo
import com.luckyfrog.quickmart.core.di.appModule
import com.luckyfrog.quickmart.core.di.dispatcherModule
import com.luckyfrog.quickmart.core.di.initKoin
import com.luckyfrog.quickmart.core.di.ktorModule
import com.luckyfrog.quickmart.core.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

class QuickMart : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidLogger(if (isDebug()) Level.ERROR else Level.NONE)
            androidContext(this@QuickMart)
        }

    }
    fun Context.isDebug() = 0 != applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE

}