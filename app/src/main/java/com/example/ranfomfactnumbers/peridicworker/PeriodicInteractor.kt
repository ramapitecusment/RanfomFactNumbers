package com.example.ranfomfactnumbers.peridicworker

interface PeriodicInteractor {

    suspend fun randomNumber()

    class Base: PeriodicInteractor {

        override suspend fun randomNumber() {
            TODO("Not yet implemented")
        }

    }

}