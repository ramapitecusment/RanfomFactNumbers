package com.example.ranfomfactnumbers

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ranfomfactnumbers.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest : BaseTest() {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

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