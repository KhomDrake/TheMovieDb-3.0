package com.vlv.core.test

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag

class SmallWarningViewMatcher(
    private val composeRule: ComposeContentTestRule,
    private val tag: String
) {
    fun isDisplayed() = apply {
        composeRule
            .onNodeWithTag(tag, useUnmergedTree = true)
            .assertIsDisplayed()
    }

    fun withTitle(text: String) = apply {
        composeRule
            .onNodeWithTag(tag, useUnmergedTree = true)
            .onChildren()
            .get(1)
            .assertTextEquals(text)
    }

    fun withBody(text: String) = apply {
        composeRule
            .onNodeWithTag(tag, useUnmergedTree = true)
            .onChildren()
            .get(2)
            .assertTextEquals(text)
    }

    fun withButtonLink(text: String) = apply {
        composeRule
            .onNodeWithTag(tag, useUnmergedTree = true)
            .onChildren()
            .get(3)
            .assertTextEquals(text)
    }
}

fun ComposeContentTestRule.smallWarningView(
    tag: String
) = SmallWarningViewMatcher(
    this,
    tag
)