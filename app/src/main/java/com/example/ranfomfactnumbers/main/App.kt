package com.example.ranfomfactnumbers.main

import android.app.Application
import com.example.ranfomfactnumbers.BuildConfig
import com.example.ranfomfactnumbers.numbers.data.cloud.CloudModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val cloudModule = if (BuildConfig.DEBUG) CloudModule.Debug() else CloudModule.Release()

    }

}