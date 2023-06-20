package com.example.ranfomfactnumbers.numbers.presentation.ui

import android.widget.TextView
import com.example.ranfomfactnumbers.main.presentation.Mapper

data class NumberUi(
    private val id: String,
    private val fact: String
) : Mapper<Boolean, NumberUi> {

    fun <T> map(mapper: Mapper<T>): T = mapper.map(id, fact)

    interface Mapper<T> {
        fun map(id: String, fact: String): T
    }

    fun map(head: TextView, subTitle: TextView) {
        head.text = id
        subTitle.text = fact
    }

    override fun map(source: NumberUi) = source.id == id

}

object DetailsUI: NumberUi.Mapper<String> {

    override fun map(id: String, fact: String) = "$id\n\n$fact"

}

class ListItemUI(
    private val head: TextView,
    private val subtitle: TextView
) : NumberUi.Mapper<Unit> {

    override fun map(id: String, fact: String) {
        head.text = id
        subtitle.text = fact
    }

}
