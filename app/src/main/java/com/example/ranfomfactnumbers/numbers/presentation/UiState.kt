package com.example.ranfomfactnumbers.numbers.presentation

import com.google.android.material.textfield.TextInputLayout

sealed class UiState {

    abstract fun apply(inputLayout: TextInputLayout)

    object Success : UiState() {
        override fun apply(inputLayout: TextInputLayout) {
            inputLayout.editText?.setText("")
        }
    }

    data class Error(private val message: String) : UiState() {
        override fun apply(inputLayout: TextInputLayout) {
            inputLayout.isErrorEnabled = true
            inputLayout.error = message
        }
    }

}
