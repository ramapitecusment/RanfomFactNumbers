package com.example.ranfomfactnumbers.main.di

import android.content.Context
import com.example.ranfomfactnumbers.numbers.data.cache.CacheModule
import com.example.ranfomfactnumbers.numbers.data.cloud.CloudModule
import com.example.ranfomfactnumbers.numbers.data.cloud.RandomApiHeader

interface ProvideCoreInstances {

    fun provideCloudModule(): CloudModule

    fun provideCacheModule(): CacheModule

    class Release(private val context: Context) : ProvideCoreInstances {

        override fun provideCloudModule() = CloudModule.Release()

        override fun provideCacheModule() = CacheModule.Base(context)

    }

    class Debug(private val context: Context) : ProvideCoreInstances {

        override fun provideCloudModule() = CloudModule.Debug()

        override fun provideCacheModule() = CacheModule.Base(context)

    }

    class Mock(private val context: Context) : ProvideCoreInstances {

        override fun provideCloudModule() = CloudModule.Mock(RandomApiHeader.Mock("test"))

        override fun provideCacheModule() = CacheModule.Mock(context)

    }

}