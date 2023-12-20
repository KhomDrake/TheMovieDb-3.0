package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.test.ComposeBaseTest
import org.junit.Test

class StateViewTest : ComposeBaseTest() {

    @Test
    fun withTitle_shouldOnlyHaveTitleAndIcon() {
        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview {
                    StateView(
                        icon = R.drawable.ic_movie_enable,
                        title = "Title Test",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        withTitle("Title Test")
        withoutBody()
        withIcon()
        withChildrenCount(2)
    }

    @Test
    fun withBody_shouldOnlyHaveBodyAndIcon() {
        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview {
                    StateView(
                        icon = R.drawable.ic_movie_enable,
                        body = "Body Test",
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        withoutTitle()
        withBody("Body Test")
        withIcon()
        withChildrenCount(2)
    }

    @Test
    fun withTitleAndBody_shouldHaveTitleAndBodyAndIcon() {
        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview {
                    StateView(
                        icon = R.drawable.ic_movie_enable,
                        title = "Title Test",
                        modifier = Modifier.fillMaxWidth(),
                        body = "Body Test"
                    )
                }
            }
        }

        withTitle("Title Test")
        withBody("Body Test")
        withIcon()
        withChildrenCount(3)
    }

    private fun withChildrenCount(count: Int) {
        composeRule
            .onNodeWithTag(StateViewTags.PARENT.name, useUnmergedTree = true)
            .onChildren()
            .assertCountEquals(count)
    }

    private fun withIcon() {
        composeRule
            .onNodeWithTag(StateViewTags.ICON.name)
            .assertIsDisplayed()
    }

    private fun withBody(text: String) {
        composeRule
            .onNodeWithTag(StateViewTags.BODY.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun withoutBody() {
        composeRule
            .onNodeWithTag(StateViewTags.BODY.name)
            .assertDoesNotExist()
    }

    private fun withTitle(text: String) {
        composeRule
            .onNodeWithTag(StateViewTags.TITLE.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun withoutTitle() {
        composeRule.onNodeWithTag(StateViewTags.TITLE.name)
            .assertDoesNotExist()
    }

}