package com.vinicius.githubapi.utils.action

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.google.android.material.tabs.TabLayout
import org.hamcrest.Matcher

class ClickTabLayout(private val position: Int) : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isDisplayed()
    }

    override fun getDescription(): String {
        return "Clicking on TabLayout ChildView position $position"
    }

    override fun perform(uiController: UiController, view: View) {
        if(view is TabLayout) {
            view.getTabAt(position)?.view?.performClick()
        }
    }
}