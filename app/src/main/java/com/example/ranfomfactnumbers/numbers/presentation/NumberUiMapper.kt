package com.example.ranfomfactnumbers.numbers.presentation

import com.example.ranfomfactnumbers.numbers.domain.NumberFact

class NumberUiMapper : NumberFact.Mapper<NumberUi> {
    override fun map(id: String, fact: String) = NumberUi(id, fact)
}
