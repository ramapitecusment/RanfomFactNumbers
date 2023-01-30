package com.example.ranfomfactnumbers.numbers.presentation

import android.view.View
import androidx.lifecycle.LifecycleOwner

interface NumbersCommunications : ObserveNumbers {

    fun showProgress(show: Int)

    fun showState(uiState: UiState)

    fun showList(list: List<NumberUi>)

    class Base(
        private val state: NumberStateCommunication,
        private val progress: ProgressCommunication,
        private val numbersList: NumbersListCommunication,
    ) : NumbersCommunications {

        override fun showProgress(show: Int) = progress.map(show)

        override fun showState(uiState: UiState) = state.map(uiState)

        override fun showList(list: List<NumberUi>) = numbersList.map(list)

        override fun observeProgress(owner: LifecycleOwner, action: (Int) -> Unit) {
            progress.observe(owner, action)
        }

        override fun observeState(owner: LifecycleOwner, action: (UiState) -> Unit) {
            state.observe(owner, action)
        }

        override fun observeList(owner: LifecycleOwner, action: (List<NumberUi>) -> Unit) {
            numbersList.observe(owner, action)
        }

    }

}

interface ObserveNumbers {

    fun observeProgress(owner: LifecycleOwner, action: (Int) -> Unit)

    fun observeState(owner: LifecycleOwner, action: (UiState) -> Unit)

    fun observeList(owner: LifecycleOwner, action: (List<NumberUi>) -> Unit)

}

interface ProgressCommunication : Communication.Mutable<Int> {
    class Base : Communication.Post<Int>(View.GONE), ProgressCommunication
}

interface NumberStateCommunication : Communication.Mutable<UiState> {
    class Base : Communication.Post<UiState>(UiState.Success), NumberStateCommunication
}

interface NumbersListCommunication : Communication.Mutable<List<NumberUi>> {
    class Base : Communication.Post<List<NumberUi>>(emptyList()), NumbersListCommunication
}