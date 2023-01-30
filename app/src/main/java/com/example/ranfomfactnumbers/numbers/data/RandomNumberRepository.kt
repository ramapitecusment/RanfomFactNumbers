package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.NumberFact

interface RandomNumberRepository {

    suspend fun randomNumberFact(): NumberFact

}