package com.example.ranfomfactnumbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val interactor: NumbersInteractor,
    private val communications: NumbersCommunications,
) : ObserveNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) {
        communications.observeProgress(owner, observer)
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
        communications.observeState(owner, observer)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
        communications.observeList(owner, observer)
    }


}