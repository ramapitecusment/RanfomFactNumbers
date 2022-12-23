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
    private val dispatchers: DispatchersList,
    private val interactor: NumbersInteractor,
    private val manageResources: ManageResources,
    private val mapper: NumbersResult.Mapper<Unit>,
    private val communications: NumbersCommunications,
) : ViewModel(), ObserveNumbers, FetchNumbers {

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) doAsync { interactor.init() }
    }

    override fun fetchRandomNumberFact() {
        doAsync { interactor.factAboutRandomNumber() }
    }

    override fun fetchNumberFact(number: String) {
        if (number.isEmpty()) {
            val text = manageResources.string(R.string.empty_number_error_message)
            communications.showState(UiState.Error(text))
        } else doAsync { interactor.factAboutNumber(number) }
    }

    private fun doAsync(block: suspend () -> NumbersResult) {
        communications.showProgress(true)
        viewModelScope.launch(dispatchers.io()) {
            val result = block.invoke()
            communications.showProgress(false)
            result.map(mapper)
        }
    }

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
