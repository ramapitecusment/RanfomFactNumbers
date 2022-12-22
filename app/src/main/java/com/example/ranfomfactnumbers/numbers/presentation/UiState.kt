package com.example.ranfomfactnumbers.numbers.presentation

sealed class UiState {

    object Success : UiState() {

        override fun equals(other: Any?): Boolean {
            return if (other is Success) true else super.equals(other)
        }

    }

    data class Error(private val message: String) : UiState()

}
