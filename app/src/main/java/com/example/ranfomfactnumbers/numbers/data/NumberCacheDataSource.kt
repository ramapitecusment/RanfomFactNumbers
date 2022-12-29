package com.example.ranfomfactnumbers.numbers.data

interface NumberCacheDataSource: FetchNumber {

    suspend fun allNumbers(): List<NumberData>

    suspend fun contains(number: String): Boolean

    suspend fun saveNumberFact(numberData: NumberData)

}

interface FetchNumber {

    suspend fun number(number: String): NumberData

}