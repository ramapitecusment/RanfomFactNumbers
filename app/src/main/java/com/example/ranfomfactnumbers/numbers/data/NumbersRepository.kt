package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.NumberFact

interface NumbersRepository {

    suspend fun allNumbers(): List<NumberFact>

    suspend fun numberFact(number: String): NumberFact

    suspend fun randomNumberFact(): NumberFact

    class Base(
        private val handleRequest: HandleDataRequest,
        private val cloudDataSource: NumberCloudDataSource,
        private val cacheDataSource: NumberCacheDataSource,
        private val mapperToDomain: NumberData.Mapper<NumberFact>,
    ) : NumbersRepository {

        override suspend fun allNumbers(): List<NumberFact> {
            val data = cacheDataSource.allNumbers()
            return data.map { it.map(mapperToDomain) }
        }

        override suspend fun numberFact(number: String) = handleRequest.handle {
            val dataSource = if (cacheDataSource.contains(number)) cacheDataSource
            else cloudDataSource
            dataSource.number(number)
        }

        override suspend fun randomNumberFact(): NumberFact = handleRequest.handle {
            cloudDataSource.randomNumber()
        }

    }

}

