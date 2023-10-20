package com.vlv.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import com.vlv.test.action.ClickIgnoreConstraint
import com.vlv.test.action.ClickTabLayout
import com.vlv.test.matcher.TabLayoutTextMatcher
import com.vlv.test.action.ClickOnChildView
import com.vlv.test.matcher.ImageDrawableMatcher
import com.vlv.test.matcher.RecyclerViewMatcherQuantityItems
import com.vlv.test.matcher.atPosition
import com.vlv.test.matcher.withRecyclerViewItem
import org.hamcrest.Matcher
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

fun Int.checkRecyclerViewQuantityOfItems(quantityOfItems: Int) {
    onView(withId(this)).check(matches(RecyclerViewMatcherQuantityItems(quantityOfItems)))
}

fun Int.checkViewOnRecyclerViewPosition(
    position: Int,
    viewMatcher: Matcher<View>,
    childId: Int = -1
) {
    onView(withRecyclerViewItem(this))
        .check(matches(atPosition(
            position,
            viewMatcher,
            childId
        )))
}

fun Int.withDrawable(drawableId: Int) =
    onView(withId(this)).check(
        matches(ImageDrawableMatcher(drawableId))
    )

fun Int.clickOnRecyclerViewInsideItem(position: Int, childId: Int) =
    onView(withId(this)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position, ClickOnChildView(childId)
        )
    )

fun Int.clickOnRecyclerViewItem(position: Int) =
    onView(withId(this)).perform(
        RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
            position, ViewActions.click()
        )
    )
