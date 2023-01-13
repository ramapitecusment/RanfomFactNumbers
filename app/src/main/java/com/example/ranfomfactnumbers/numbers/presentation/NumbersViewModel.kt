package com.example.ranfomfactnumbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult
import kotlinx.coroutines.launch

class NumbersViewModel(
    private val interactor: NumbersInteractor,
    private val manageResources: ManageResources,
    private val communications: NumbersCommunications,
    private val handleNumbersRequest: HandleNumbersRequest,
) : ViewModel(), ObserveNumbers, FetchNumbers {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) handleNumbersRequest.handle(viewModelScope) { interactor.init() }
    }

    override fun fetchRandomNumberFact() {
        handleNumbersRequest.handle(viewModelScope) { interactor.factAboutRandomNumber() }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty())
            communications.showState(UiState.Error(manageResources.string(R.string.empty_number_error_message)))
        else
            handleNumbersRequest.handle(viewModelScope) { interactor.factAboutNumber(number) }
    }

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Int>) {
        communications.observeProgress(owner, observer)
    }

    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) {
        communications.observeState(owner, observer)
    }

    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) {
        communications.observeList(owner, observer)
    }

}