package com.example.ranfomfactnumbers.numbers.domain

data class NumberFact(
    private val id: String,
    private val fact: String
) {

    interface Mapper<T> {
        fun map(id: String, fact: String): T
    }

    fun <T> map(mapper: Mapper<T>) = mapper.map(id, fact)

}
