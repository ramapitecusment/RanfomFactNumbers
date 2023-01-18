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
import org.koin.dsl.bind
import org.koin.dsl.module
import java.lang.Exception

val numbersModule = module {

    viewModel {
        val communications = NumbersCommunications.Base(get(), get(), get())

        NumbersViewModel(
            get(),
            get(),
            communications,
            HandleNumbersRequest.Base(get(), communications, NumbersResultMapper(get(), communications))
        )
    }

    factory<ProgressCommunication> { ProgressCommunication.Base() }
    factory<NumberStateCommunication> { NumberStateCommunication.Base() }
    factory<NumbersListCommunication> { NumbersListCommunication.Base() }

    factory<NumberFact.Mapper<NumberUi>> { NumberUiMapper() }

    factory<NumbersInteractor> { NumbersInteractor.Base(get(), get()) }
    // TODO Check how to inject HandleError.Base
    factory<HandleRequest> { HandleRequest.Base(HandleError.Base(get()), get()) }

    factory<NumbersRepository> { NumbersRepository.Base(get(), get(), get(), get()) }
    factory<HandleDataRequest> { HandleDataRequest.Base(get(), get(), get()) }
    factory<HandleError<Exception>> { HandleDomainError() }

    factory<NumberCloudDataSource> { NumberCloudDataSource.Base(get()) }

    single { get<CloudModule>().service(NumbersService::class.java) }

    // TODO Check how to inject NumberDataToCache
    factory<NumberCacheDataSource> { NumberCacheDataSource.Base(get(), NumberDataToCache()) }
    single { get<CacheModule>().provideDatabase().numbersDao() }

    factory<NumberData.Mapper<NumberFact>> { NumberDataToDomain() }

}