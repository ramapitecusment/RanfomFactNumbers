package com.example.ranfomfactnumbers.numbers.data

import com.example.ranfomfactnumbers.numbers.domain.NumberFact

class NumberDataToDomain : NumberData.Mapper<NumberFact> {

    override fun map(id: String, fact: String) = NumberFact(id, fact)

}