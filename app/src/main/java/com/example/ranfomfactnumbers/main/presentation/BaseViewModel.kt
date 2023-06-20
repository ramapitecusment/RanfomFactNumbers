package com.example.ranfomfactnumbers.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    private val dispatchersList: DispatchersList
) : ViewModel(), Handle {

    override fun <T : Any> handle(
        block: suspend () -> T,
        ui: (T) -> Unit
    ) = viewModelScope.launch(dispatchersList.io()) {
        val result = block.invoke()
        ui.invoke(result)
    }


}

interface Handle {

    fun <T : Any> handle(block: suspend () -> T, ui: (T) -> Unit): Job

}