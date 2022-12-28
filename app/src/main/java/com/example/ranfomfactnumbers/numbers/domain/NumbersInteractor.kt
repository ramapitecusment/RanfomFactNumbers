package com.example.ranfomfactnumbers.numbers.domain

import com.example.ranfomfactnumbers.numbers.data.NumbersRepository

interface NumbersInteractor {

    suspend fun init(): NumbersResult

    suspend fun factAboutNumber(number: String): NumbersResult

    suspend fun factAboutRandomNumber(): NumbersResult

    class Base(
        private val handleRequest: HandleRequest,
        private val repository: NumbersRepository,
    ) : NumbersInteractor {

        override suspend fun init(): NumbersResult = NumbersResult.Success(repository.allNumbers())

        override suspend fun factAboutNumber(number: String): NumbersResult = handleRequest.handle {
            repository.numberFact(number)
        }

        override suspend fun factAboutRandomNumber(): NumbersResult = handleRequest.handle {
            repository.randomNumberFact()
        }

    }

}