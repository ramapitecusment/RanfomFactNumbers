package com.example.ranfomfactnumbers.numbers.presentation

interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}

interface ClearError {

    fun clearError()

}