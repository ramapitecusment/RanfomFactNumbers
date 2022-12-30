package com.example.ranfomfactnumbers.numbers.data.cache

import com.example.ranfomfactnumbers.numbers.data.NumberData

interface NumberCacheDataSource: FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumber(numberData: NumberData)

}

interface FetchNumber {

    suspend fun number(number: String): NumberData

}