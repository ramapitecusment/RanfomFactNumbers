package com.example.ranfomfactnumbers.numbers.presentation.ui

import androidx.lifecycle.LifecycleOwner
import com.example.ranfomfactnumbers.main.presentation.BaseViewModel
import com.example.ranfomfactnumbers.main.presentation.DispatchersList
import com.example.ranfomfactnumbers.main.presentation.UiFeature
import com.example.ranfomfactnumbers.numbers.presentation.feature.NumbersFactFeature

class NumbersViewModel(
    dispatchersList: DispatchersList,
    private val initial: UiFeature,
    private val randomNumberFact: UiFeature,
    private val numberFact: NumbersFactFeature,
    private val communications: NumbersCommunications,
) : BaseViewModel(dispatchersList), ObserveNumbers, FetchNumbers, ClearError {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) initial.handle(this)
    }

    override fun fetchRandomNumberFact() {
        randomNumberFact.handle(this)
    }

    override fun fetchNumberFact(number: String) {
        numberFact.number(number).handle(this)
    }

    override fun observeProgress(owner: LifecycleOwner, action: (value: Int) -> Unit) {
        communications.observeProgress(owner, action)
    }

    override fun observeState(owner: LifecycleOwner, action: (value: UiState) -> Unit) {
        communications.observeState(owner, action)
    }

    override fun observeList(owner: LifecycleOwner, action: (List<NumberUi>) -> Unit) {
        communications.observeList(owner, action)
    }

    override fun clearError() {
        communications.showState(UiState.ClearError)
    }

}