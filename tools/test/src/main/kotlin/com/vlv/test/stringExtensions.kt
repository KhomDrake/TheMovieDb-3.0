package com.vlv.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.vlv.test.action.ClickIgnoreConstraint

fun String.isDisplayed() {
    onView(withText(this)).check(matches(ViewMatchers.isDisplayed()))
}

fun String.clickIgnoreConstraint() {
    onView(withText(this)).apply {
        perform(scrollTo(), ClickIgnoreConstraint())
    }
    Thread.sleep(100)
}