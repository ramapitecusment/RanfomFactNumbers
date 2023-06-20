package com.example.ranfomfactnumbers.numbers.presentation.ui

import com.google.android.material.textfield.TextInputLayout

sealed class UiState {

    abstract fun apply(inputLayout: TextInputLayout)

    object Success : UiState() {
        override fun apply(inputLayout: TextInputLayout) {
            inputLayout.editText?.setText("")
        }
    }

    abstract class AbstractError(
        private val message: String,
        private val errorEnabled: Boolean
    ) : UiState() {
        override fun apply(inputLayout: TextInputLayout) {
            inputLayout.isErrorEnabled = errorEnabled
            inputLayout.error = message
        }
    }

    data class Error(private val message: String) : AbstractError(message, true)

    object ClearError : AbstractError("", false)

}
