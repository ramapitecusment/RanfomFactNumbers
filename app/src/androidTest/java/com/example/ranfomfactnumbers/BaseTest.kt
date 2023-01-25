package com.example.ranfomfactnumbers

import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText

abstract class BaseTest {

    private fun Int.view() = onView(withId(this))

    protected fun Int.typeText(value: String): ViewInteraction? {
        val result = view().perform(ViewActions.typeText(value))
        closeSoftKeyboard()
        return result
    }

    protected fun Int.checkText(value: String) = view().check(matches(withText(value)))

    protected fun Int.click() = view().perform(ViewActions.click())

    private fun Int.viewInRecycler(position: Int, viewId: Int) =
        onView(RecyclerViewMatcher(this).atPosition(position, viewId))

    private fun ViewInteraction.checkText(value: String) = check(matches(withText(value)))

    private fun ViewInteraction.click() = perform(ViewActions.click())

    protected fun Int.checkRecyclerText(position: Int, viewId: Int, value: String) =
        viewInRecycler(position, viewId).checkText(value)

    protected fun Int.clickRecyclerItem(position: Int, viewId: Int) =
        viewInRecycler(position, viewId).click()

}
