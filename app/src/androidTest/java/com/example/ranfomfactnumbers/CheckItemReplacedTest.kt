package com.example.ranfomfactnumbers

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ranfomfactnumbers.main.presentation.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CheckItemReplacedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_no_duplicated_items() {
        // enter 1
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("1"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFact)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.title)).check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subtitle)).check(matches(withText("fact about 1")))

        // enter 2
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("2"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFact)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.title)).check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subtitle)).check(matches(withText("fact about 2")))
        // check 1 is below 2
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.title)).check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subtitle)).check(matches(withText("fact about 1")))

        // enter 1 again
        onView(ViewMatchers.withId(R.id.editText)).perform(typeText("1"))
        closeSoftKeyboard()
        onView(ViewMatchers.withId(R.id.getFact)).perform(click())

        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.title)).check(matches(withText("1")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(0, R.id.subtitle)).check(matches(withText("fact about 1")))
        // check 2 is below 1
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.title)).check(matches(withText("2")))
        onView(RecyclerViewMatcher(R.id.historyRecyclerView).atPosition(1, R.id.subtitle)).check(matches(withText("fact about 2")))
    }

}