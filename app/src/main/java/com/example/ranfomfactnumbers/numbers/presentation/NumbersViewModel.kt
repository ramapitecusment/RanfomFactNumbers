package com.example.ranfomfactnumbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ranfomfactnumbers.R
import com.example.ranfomfactnumbers.numbers.domain.NumbersInteractor

class NumbersViewModel(
    private val interactor: NumbersInteractor,
    private val manageResources: ManageResources,
    private val communications: NumbersCommunications,
    private val handleNumbersRequest: HandleNumbersRequest,
) : ViewModel(), ObserveNumbers, FetchNumbers, ClearError {

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