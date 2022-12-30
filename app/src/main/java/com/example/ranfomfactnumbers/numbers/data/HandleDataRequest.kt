package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.data.cache.NumberCacheDataSource
import com.example.ranfomfactnumbers.numbers.domain.HandleError
import com.example.ranfomfactnumbers.numbers.domain.NumberFact

interface HandleDataRequest {

    suspend fun handle(block: suspend () -> NumberData): NumberFact

    class Base(
        private val handleError: HandleError<Exception>,
        private val cacheDataSource: NumberCacheDataSource,
        private val mapperToDomain: NumberData.Mapper<NumberFact>,
    ) : HandleDataRequest {

        override suspend fun handle(block: suspend () -> NumberData) = try {
            val result = block.invoke()
            cacheDataSource.saveNumber(result)
            result.map(mapperToDomain)
        } catch (e: Exception) {
            throw handleError.handle(e)
        }
    }

}