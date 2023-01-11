package com.example.ranfomfactnumbers.numbers.data.cache

import com.example.ranfomfactnumbers.numbers.data.NumberData

interface NumberCacheDataSource : FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)

    class Base(
        private val dao: NumbersDao,
        private val mapper: NumberData.Mapper<NumberCache>
    ) : NumberCacheDataSource {

        override suspend fun allNumbers(): List<NumberData> {
            val data = dao.allNumbers()
            return data.map { NumberData(it.number, it.fact) }
        }

        override suspend fun contains(number: String): Boolean {
            val data = dao.number(number)
            return data != null
        }

        override suspend fun saveNumber(numberData: NumberData) {
            dao.insert(numberData.map(mapper))
        }

        override suspend fun number(number: String): NumberData {
            val numberCache = dao.number(number) ?: NumberCache("", "", 0)
            return NumberData(numberCache.number, numberCache.fact)
        }

    }
}

interface FetchNumber {

    suspend fun number(number: String): NumberData

}