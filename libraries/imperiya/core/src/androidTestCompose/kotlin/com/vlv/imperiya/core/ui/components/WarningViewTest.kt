package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.test.ComposeBaseTest
import org.junit.Test

class WarningViewTest : ComposeBaseTest() {

    private fun warningViewContent(
        title: String,
        body: String,
        linkActionText: String? = null,
        showCloseIcon: Boolean = true,
    ) {
        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview {
                    WarningView(
                        title = title,
                        body = body,
                        linkActionText = linkActionText,
                        showCloseIcon = showCloseIcon,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }

    @Test
    fun withDefaultWarningView_shouldShowRightContent() {
        warningViewContent(
            title = "Error Title",
            body = "Error Body",
            linkActionText = "Try again button text",
            showCloseIcon = true
        )

        composeRule
            .withWarningIcon()
            .withCloseIcon()
            .withTitle("Error Title")
            .withBody("Error Body")
            .withButtonLink("Try again button text")
    }

    @Test
    fun withoutCloseIcon_shouldNotShowCloseIcon() {
        warningViewContent(
            title = "Error Title",
            body = "Error Body",
            linkActionText = "Try again button text",
            showCloseIcon = false
        )

        composeRule
            .withWarningIcon()
            .withoutCloseIcon()
            .withTitle("Error Title")
            .withBody("Error Body")
            .withButtonLink("Try again button text")
    }

    @Test
    fun withoutButtonLink_shouldNotShowButtonLink() {
        warningViewContent(
            title = "Error Title",
            body = "Error Body",
            linkActionText = null,
            showCloseIcon = true
        )

        composeRule
            .withWarningIcon()
            .withCloseIcon()
            .withTitle("Error Title")
            .withBody("Error Body")
            .withoutButtonLink()
    }

    private fun ComposeContentTestRule.withTitle(text: String) = apply {
        onNodeWithTag(WarningViewTags.TITLE.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withBody(text: String) = apply {
        onNodeWithTag(WarningViewTags.BODY.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withButtonLink(text: String) = apply {
        onNodeWithTag(WarningViewTags.BUTTON_LINK.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withoutButtonLink() = apply {
        onNodeWithTag(WarningViewTags.BUTTON_LINK.name)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.withCloseIcon() = apply {
        onNodeWithTag(WarningViewTags.CLOSE_ICON.name)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withoutCloseIcon() = apply {
        onNodeWithTag(WarningViewTags.CLOSE_ICON.name)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.withWarningIcon() = apply {
        onNodeWithTag(WarningViewTags.WARNING_ICON.name)
            .assertIsDisplayed()
    }

}