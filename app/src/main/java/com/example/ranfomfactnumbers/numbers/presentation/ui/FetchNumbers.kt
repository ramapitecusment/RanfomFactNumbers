package com.example.ranfomfactnumbers.numbers.presentation.ui

interface FetchNumbers {

    fun init(isFirstRun: Boolean)

    fun fetchRandomNumberFact()

    fun fetchNumberFact(number: String)
}

interface ClearError {

    fun clearError()

}