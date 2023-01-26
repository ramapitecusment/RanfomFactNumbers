package com.example.ranfomfactnumbers

import org.junit.Test

class RandomFactTest : BaseTest() {

    @Test
    fun test() {
        val numbersPage = NumbersPage()
        numbersPage.run {
            getRandomFactButton.click()
            recycler.checkRecyclerText(0, title, "1")
            recycler.checkRecyclerText(0, subtitle, "fact about 1")

            getRandomFactButton.click()
            recycler.checkRecyclerText(0, title, "2")
            recycler.checkRecyclerText(0, subtitle, "fact about 2")
            recycler.checkRecyclerText(1, title, "1")
            recycler.checkRecyclerText(1, subtitle, "fact about 1")
        }

    }

}