package com.vlv.test.action

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher

class ClickIgnoreConstraint : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return ViewMatchers.isEnabled()
    }

    override fun getDescription(): String {
        return "Clicking on view ignoring constraint"
    }

    override fun perform(uiController: UiController, view: View) {
        view.performClick()
    }
}