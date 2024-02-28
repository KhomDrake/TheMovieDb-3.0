package com.vlv.test.action

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class ClickOnChildView(private val childId: Int) : ViewAction {
    override fun getDescription() = ""

    override fun getConstraints(): Matcher<View> = ViewMatchers.isEnabled()

    override fun perform(uiController: UiController?, view: View?) {
        view?.findViewById<View>(childId)?.performClick()
    }
}