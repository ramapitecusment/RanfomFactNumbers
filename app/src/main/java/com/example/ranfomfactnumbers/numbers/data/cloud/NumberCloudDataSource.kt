package com.example.ranfomfactnumbers.numbers.data.cloud

import com.example.ranfomfactnumbers.numbers.data.cache.FetchNumber
import com.example.ranfomfactnumbers.numbers.data.NumberData

interface NumberCloudDataSource : FetchNumber {

    suspend fun randomNumber(): NumberData

    class Base(
        private val service: NumbersService,
        private val randomHeaders: RandomApiHeader
    ) : NumberCloudDataSource {

        override suspend fun number(number: String): NumberData {
            val body = service.fact(number)
            return NumberData(number, body)
        }

        override suspend fun randomNumber(): NumberData {
            val response = service.random()
            val body = response.body() ?: throw IllegalStateException("Service is unavailable")
            val headers = response.headers()
            randomHeaders.findInHeader(headers)?.let { (_, value) ->
                return NumberData(value, body)
            }

            throw IllegalStateException("Service is unavailable")
        }

    }

}