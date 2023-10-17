package com.vlv.test

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.vinicius.githubapi.utils.action.ClickIgnoreConstraint
import com.vinicius.githubapi.utils.action.ClickTabLayout
import com.vinicius.githubapi.utils.matcher.TabLayoutTextMatcher
import org.hamcrest.Matchers.not

fun Int.hasText(text: String) {
    onView(withId(this)).check(matches(withText(text)))
}

fun Int.typeText(text: String): ViewInteraction = onView(withId(this))
    .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())

fun Int.hasHint(text: String) {
    onView(withId(this)).check(matches(withHint(text)))
}

fun Int.isDisplayed() {
    onView(withId(this)).check(matches(ViewMatchers.isDisplayed()))
}

fun Int.isNotDisplayed() {
    onView(withId(this)).check(matches(not(ViewMatchers.isDisplayed())))
}

fun Int.click() {
    onView(withId(this)).perform(ViewActions.click())
}

fun Int.clickIgnoreConstraint() {
    onView(withId(this)).perform(ClickIgnoreConstraint())
}

fun Int.clickRecyclerViewItemPosition(position: Int) {
    onView(withId(this))
        .perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position,
                ClickIgnoreConstraint()
            )
        )
}

fun Int.clickTabLayoutPosition(position: Int) {
    onView(withId(this)).perform(ClickTabLayout(position))
}

fun Int.checkTextTabLayoutPosition(text: String, position: Int) {
    onView(withId(this)).check(matches(TabLayoutTextMatcher(position, text)))
}

fun Int.scrollToPosition(position: Int) {
    onView(withId(this)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
}

