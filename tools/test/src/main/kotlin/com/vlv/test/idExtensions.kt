package com.vlv.test

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.platform.app.InstrumentationRegistry
import com.vlv.test.action.ClickIgnoreConstraint
import com.vlv.test.action.ClickOnChildView
import com.vlv.test.action.ClickTabLayout
import com.vlv.test.matcher.ImageDrawableMatcher
import com.vlv.test.matcher.RecyclerViewMatcherQuantityItems
import com.vlv.test.matcher.TabLayoutTextMatcher
import com.vlv.test.matcher.atPosition
import com.vlv.test.matcher.withRecyclerViewItem
import org.hamcrest.Matcher
import org.hamcrest.Matchers.not

fun runWithWaitFor(block: () -> Unit) {
    runCatching {
        block.invoke()
    }.onFailure {
        Thread.sleep(1000)
        block.invoke()
    }
}

fun Int.hasText(text: String) {
    runWithWaitFor {
        onView(withId(this)).check(matches(withText(text)))
    }
}

fun Int.typeText(text: String) = runWithWaitFor {
    onView(withId(this))
        .perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())
}

fun Int.hasHint(text: String) {
    runWithWaitFor {
        onView(withId(this)).check(matches(withHint(text)))
    }
}

fun Int.isDisplayed() {
    runWithWaitFor {
        onView(withId(this)).check(matches(ViewMatchers.isDisplayed()))
    }
}

fun Int.clickOnString(targetContext: Boolean = false) {
    val context = if(targetContext) InstrumentationRegistry.getInstrumentation().targetContext
        else InstrumentationRegistry.getInstrumentation().context
    context.getString(this).clickIgnoreConstraint()
}

fun Int.isNotDisplayed() {
    runWithWaitFor {
        onView(withId(this)).check(matches(not(ViewMatchers.isDisplayed())))
    }
}

fun Int.click() {
    runWithWaitFor {
        onView(withId(this)).perform(ViewActions.click())
    }
}

fun Int.clickIgnoreConstraint() {
    runWithWaitFor {
        onView(withId(this)).perform(ClickIgnoreConstraint())
        Thread.sleep(100)
    }
}

fun Int.clickRecyclerViewItemPosition(position: Int) {
    runWithWaitFor {
        onView(withId(this))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    position,
                    ClickIgnoreConstraint()
                )
            )
        Thread.sleep(100)
    }
}

fun Int.clickTabLayoutPosition(position: Int) {
    runWithWaitFor {
        onView(withId(this)).perform(ClickTabLayout(position))
    }
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
    runWithWaitFor {
        onView(withRecyclerViewItem(this))
            .check(matches(atPosition(
                position,
                viewMatcher,
                childId
            )))
    }
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

fun Int.clickOnRecyclerViewItem(position: Int) {
    runWithWaitFor {
        onView(withId(this)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                position, ViewActions.click()
            )
        )
        Thread.sleep(300)
    }
}

