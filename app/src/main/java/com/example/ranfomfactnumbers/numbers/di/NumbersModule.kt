package com.example.ranfomfactnumbers.numbers.di

import com.example.ranfomfactnumbers.numbers.data.*
import com.example.ranfomfactnumbers.numbers.data.cache.CacheModule
import com.example.ranfomfactnumbers.numbers.data.cache.NumberCache
import com.example.ranfomfactnumbers.numbers.data.cache.NumberCacheDataSource
import com.example.ranfomfactnumbers.numbers.data.cache.NumberDataToCache
import com.example.ranfomfactnumbers.numbers.data.cloud.CloudModule
import com.example.ranfomfactnumbers.numbers.data.cloud.NumberCloudDataSource
import com.example.ranfomfactnumbers.numbers.data.cloud.NumbersService
import com.example.ranfomfactnumbers.numbers.domain.HandleError
import com.example.ranfomfactnumbers.numbers.domain.HandleRequest
import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor
import com.example.ranfomfactnumbers.numbers.presentation.*
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val numbersModule = module {

    viewModel {
        val communications = NumbersCommunications.Base(get(), get(), get())
        val mapper = NumbersResultMapper(get(), communications)
        val handleRequest = HandleNumbersRequest.Base(get(), communications, mapper)
        NumbersViewModel(get(), get(), communications, handleRequest)
    }

    factory<ProgressCommunication> { ProgressCommunication.Base() }
    factory<NumberStateCommunication> { NumberStateCommunication.Base() }
    factory<NumbersListCommunication> { NumbersListCommunication.Base() }

    factory<NumberFact.Mapper<NumberUi>> { NumberUiMapper() }

    factory<NumbersInteractor> { NumbersInteractor.Base(get(), get()) }
    factory<HandleError<String>> { HandleError.Base(get()) }
    factory<HandleRequest> { HandleRequest.Base(get(), get()) }

    factory<NumbersRepository> { NumbersRepository.Base(get(), get(), get(), get()) }
    factory<HandleDataRequest> { HandleDataRequest.Base(get<HandleDomainError>(), get(), get()) }
    factory { HandleDomainError() }

    factory<NumberCloudDataSource> { NumberCloudDataSource.Base(get()) }

    single { get<CloudModule>().service(NumbersService::class.java) }

    factory<NumberCacheDataSource> { NumberCacheDataSource.Base(get(), NumberDataToCache()) }
    single { get<CacheModule>().provideDatabase().numbersDao() }

    factory<NumberData.Mapper<NumberFact>> { NumberDataToDomain() }

}