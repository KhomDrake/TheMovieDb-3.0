package com.vlv.test

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.assertion.ViewAssertions.matches

fun String.isDisplayed() {
    onView(withText(this)).check(matches(ViewMatchers.isDisplayed()))
}