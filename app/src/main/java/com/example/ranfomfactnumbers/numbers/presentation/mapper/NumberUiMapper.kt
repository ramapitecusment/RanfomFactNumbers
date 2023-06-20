package com.example.ranfomfactnumbers.numbers.presentation.mapper

import com.example.ranfomfactnumbers.numbers.domain.NumberFact
import com.example.ranfomfactnumbers.numbers.presentation.ui.NumberUi

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String) = NumberUi(id, fact)
}
