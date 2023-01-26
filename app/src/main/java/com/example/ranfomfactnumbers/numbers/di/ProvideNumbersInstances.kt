package com.example.ranfomfactnumbers.numbers.di

import com.example.ranfomfactnumbers.numbers.data.cloud.CloudModule
import com.example.ranfomfactnumbers.numbers.data.cloud.MockNumbersService
import com.example.ranfomfactnumbers.numbers.data.cloud.NumbersService
import com.example.ranfomfactnumbers.numbers.data.cloud.RandomApiHeader

interface ProvideNumbersInstances {

    fun provideNumbersService(): NumbersService

    fun provideRandomApiHeader(): RandomApiHeader

    class Release(private val cloudModule: CloudModule) : ProvideNumbersInstances {

        override fun provideNumbersService() = cloudModule.service(NumbersService::class.java)

        override fun provideRandomApiHeader() = RandomApiHeader.Base()

    }

    class Debug(private val cloudModule: CloudModule) : ProvideNumbersInstances {

        override fun provideNumbersService() = cloudModule.service(NumbersService::class.java)

        override fun provideRandomApiHeader() = RandomApiHeader.Base()

    }

    class Mock(private val cloudModule: CloudModule) : ProvideNumbersInstances {

        override fun provideNumbersService() = cloudModule.service(MockNumbersService::class.java)

        override fun provideRandomApiHeader() = RandomApiHeader.Mock()

    }

}