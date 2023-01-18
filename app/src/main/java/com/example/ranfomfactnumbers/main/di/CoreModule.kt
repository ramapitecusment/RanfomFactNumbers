package com.example.ranfomfactnumbers.main.di

import com.example.ranfomfactnumbers.BuildConfig
import com.example.ranfomfactnumbers.numbers.data.cache.CacheModule
import com.example.ranfomfactnumbers.numbers.data.cloud.CloudModule
import com.example.ranfomfactnumbers.numbers.presentation.DispatchersList
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

    when (BuildConfig.BUILD_TYPE) {
        "staging" -> {
            single<CloudModule> { CloudModule.Mock() }
            single<CacheModule> { CacheModule.Mock(androidContext()) }
        }
        "debug" -> {
            single<CloudModule> { CloudModule.Debug() }
            single<CacheModule> { CacheModule.Base(androidContext()) }
        }
        "release" -> {
            single<CloudModule> { CloudModule.Release() }
            single<CacheModule> { CacheModule.Base(androidContext()) }
        }
    }

    single<DispatchersList> { DispatchersList.Base() }
    single<ManageResources> { ManageResources.Base(androidContext()) }


}