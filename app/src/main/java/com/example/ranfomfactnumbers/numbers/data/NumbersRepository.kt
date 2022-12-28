package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.NumberFact

interface NumbersRepository {

    suspend fun allNumbers(): List<NumberFact>

    suspend fun numberFact(number: String): NumberFact

    suspend fun randomNumberFact(): NumberFact

    class Base(): NumbersRepository {
        override suspend fun allNumbers(): List<NumberFact> {
            TODO("Not yet implemented")
        }

        override suspend fun numberFact(number: String): NumberFact {
            TODO("Not yet implemented")
        }

        override suspend fun randomNumberFact(): NumberFact {
            TODO("Not yet implemented")
        }

    }

}