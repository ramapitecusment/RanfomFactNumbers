package com.example.ranfomfactnumbers

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ranfomfactnumbers.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest : BaseTest(){

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_no_duplicated_items(): Unit = NumbersPage().run {
        // enter 1
        input.typeText("1")
        getFactButton.click()

        recycler.checkRecyclerText(0, title, "1")
        recycler.checkRecyclerText(0, subtitle, "fact about 1")

        // enter 2
        input.typeText("2")
        getFactButton.click()


        // check 1 is below 2
        recycler.checkRecyclerText(0, title, "2")
        recycler.checkRecyclerText(0, subtitle, "fact about 2")
        recycler.checkRecyclerText(1, title, "1")
        recycler.checkRecyclerText(1, subtitle, "fact about 1")

        // enter 1 again
        input.typeText("1")
        getFactButton.click()

        // check 2 is below 1
        recycler.checkRecyclerText(0, title, "1")
        recycler.checkRecyclerText(0, subtitle, "fact about 1")
        recycler.checkRecyclerText(1, title, "2")
        recycler.checkRecyclerText(1, subtitle, "fact about 2")
    }

}