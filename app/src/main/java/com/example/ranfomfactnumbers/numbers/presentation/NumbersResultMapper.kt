package com.example.ranfomfactnumbers.numbers.presentation

import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.domain.NumbersResult

class NumbersResultMapper(
    private val mapper: NumberFact.Mapper<NumberUi>,
    private val communications: NumbersCommunications
) : NumbersResult.Mapper<Unit> {

    override fun map(list: List<NumberFact>, errorMessage: String) {
        communications.showState(
            if (errorMessage.isEmpty()) {
                val numbersList = list.map { it.map(mapper) }
                if (numbersList.isNotEmpty()) communications.showList(numbersList)
                UiState.Success
            } else {
                UiState.Error(errorMessage)
            }
        )
    }

}