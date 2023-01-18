package com.example.ranfomfactnumbers

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
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
class NavigationTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_details_navigation() {
        // actions
        onView(withId(R.id.editText)).perform(typeText("10"))
        onView(withId(R.id.getFact)).perform(click())

        // check
        onView(withId(R.id.title)).check(matches(withText("10")))
        onView(withId(R.id.subtitle)).check(matches(withText("fact about 10")))

        // navigate to Details
        onView(withId(R.id.subtitle)).perform(click())

        // check
        onView(withId(R.id.details)).check(matches(withText("10\n\nfact about 10")))

        pressBack()
        onView(withId(R.id.title)).check(matches(withText("10")))
        onView(withId(R.id.subtitle)).check(matches(withText("fact about 10")))
    }

}