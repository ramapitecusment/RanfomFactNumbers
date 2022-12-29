package com.example.ranfomfactnumbers.numbers.data

interface NumberCloudDataSource: FetchNumber {

    suspend fun randomNumber(): NumberData

}