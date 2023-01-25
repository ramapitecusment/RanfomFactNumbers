package com.example.ranfomfactnumbers

import androidx.test.espresso.Espresso.*
import org.junit.Test

class NavigationTest : BaseTest() {

    @Test
    fun test_details_navigation() {
        val numbersPage = NumbersPage()
        val detailsPage = DetailsPage()
        numbersPage.run {
            input.typeText("10")
            getFactButton.click()
            recycler.checkRecyclerText(0, title, "10")
            recycler.checkRecyclerText(0, subtitle, "fact about 10")
            recycler.clickRecyclerItem(0, subtitle)
        }

        detailsPage.run {
            details.checkText("10\n\nfact about 10")
        }
        pressBack()

        numbersPage.run {
            recycler.checkRecyclerText(0, title, "10")
            recycler.checkRecyclerText(0, subtitle, "fact about 10")
        }
    }

}