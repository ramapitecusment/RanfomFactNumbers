package com.example.ranfomfactnumbers.main.di

import com.example.ranfomfactnumbers.BuildConfig
import com.example.ranfomfactnumbers.BuildType.DEBUG
import com.example.ranfomfactnumbers.BuildType.RELEASE
import com.example.ranfomfactnumbers.BuildType.MOCK
import com.example.ranfomfactnumbers.numbers.presentation.DispatchersList
import com.example.ranfomfactnumbers.numbers.presentation.ManageResources
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {

    single {
        when (BuildConfig.BUILD_TYPE) {
            MOCK -> ProvideCoreInstances.Mock(androidContext())
            DEBUG -> ProvideCoreInstances.Debug(androidContext())
            RELEASE -> ProvideCoreInstances.Release(androidContext())
            else -> throw IllegalStateException("No such build type: ${BuildConfig.BUILD_TYPE}")
        }
    }

    single { get<ProvideCoreInstances>().provideCloudModule() }
    single { get<ProvideCoreInstances>().provideCacheModule() }

    single<DispatchersList> { DispatchersList.Base() }
    single<ManageResources> { ManageResources.Base(androidContext()) }

}