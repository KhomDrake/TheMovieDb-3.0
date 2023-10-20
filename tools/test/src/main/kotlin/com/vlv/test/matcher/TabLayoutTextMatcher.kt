package com.vlv.test.matcher

import android.view.View
import androidx.test.espresso.matcher.BoundedMatcher
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Description

class TabLayoutTextMatcher(private val position: Int, private val text: String) : BoundedMatcher<View, TabLayout>(TabLayout::class.java) {
    override fun describeTo(description: Description?) {
        description?.appendText("Check text on position $position : $text ")
    }

    override fun matchesSafely(item: TabLayout): Boolean {
        return item.getTabAt(position)?.let {
            it.text.toString() == text
        } ?: false
    }
}