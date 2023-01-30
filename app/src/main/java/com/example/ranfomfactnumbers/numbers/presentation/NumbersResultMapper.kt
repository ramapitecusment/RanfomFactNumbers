package com.example.ranfomfactnumbers.numbers.presentation

import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val mapper: NumberFact.Mapper<NumberUi>,
    private val communications: NumbersCommunications
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>) {
        if (list.isNotEmpty()) communications.showList(list.map { it.map(mapper) })
        communications.showState(UiState.Success)
    }

    override fun map(errorMessage: String) {
        communications.showState(UiState.Error(errorMessage))
    }

}