package com.luckyfrog.quickmart.core.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.paperdb.Paper

@HiltAndroidApp
class LuckyStore : Application() {

    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
    }
}