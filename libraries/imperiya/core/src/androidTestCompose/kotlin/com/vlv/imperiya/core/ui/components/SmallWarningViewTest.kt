package com.vlv.imperiya.core.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.test.ComposeBaseTest
import org.junit.Test

class SmallWarningViewTest : ComposeBaseTest() {

    private fun smallWarningViewTestContent(
        title: String?,
        body: String?,
        linkActionText: String,
        showIconInfo: Boolean = true,
    ) {
        composeRule.setContent {
            TheMovieDbAppTheme {
                BackgroundPreview {
                    SmallWarningView(
                        title = title,
                        body = body,
                        linkActionText = linkActionText,
                        showIconInfo = showIconInfo
                    )
                }
            }
        }
    }

    @Test
    fun default_shouldShowRightItems() {
        smallWarningViewTestContent(
            title = "Failure title you put it here",
            body = "Failure body you pit here",
            linkActionText = "Text button",
            showIconInfo = true
        )

        composeRule
            .withTitle("Failure title you put it here")
            .withBody("Failure body you pit here")
            .withActionText("Text button")
            .withIcon()
    }

    @Test
    fun withoutIconInfo_shouldNotShowIconInfo() {
        smallWarningViewTestContent(
            title = "Failure title you put it here",
            body = "Failure body you pit here",
            linkActionText = "Text button",
            showIconInfo = false
        )

        composeRule
            .withTitle("Failure title you put it here")
            .withBody("Failure body you pit here")
            .withActionText("Text button")
            .withoutIcon()
    }

    @Test
    fun withoutTitle_shouldNotShowTitle() {
        smallWarningViewTestContent(
            title = null,
            body = "Failure body you pit here",
            linkActionText = "Text button",
            showIconInfo = false
        )

        composeRule
            .withoutTitle()
            .withBody("Failure body you pit here")
            .withActionText("Text button")
            .withoutIcon()
    }

    @Test
    fun withoutBody_shouldNotShowBody() {
        smallWarningViewTestContent(
            title = "Failure title you put it here",
            body = null,
            linkActionText = "Text button",
            showIconInfo = false
        )

        composeRule
            .withTitle("Failure title you put it here")
            .withoutBody()
            .withActionText("Text button")
            .withoutIcon()
    }

    private fun ComposeContentTestRule.withBody(text: String) = apply {
        onNodeWithTag(SmallWarningViewTags.BODY.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withoutBody() = apply {
        onNodeWithTag(SmallWarningViewTags.BODY.name)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.withTitle(text: String) = apply {
        onNodeWithTag(SmallWarningViewTags.TITLE.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withoutTitle() = apply {
        onNodeWithTag(SmallWarningViewTags.TITLE.name)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.withIcon() = apply {
        onNodeWithTag(SmallWarningViewTags.ICON_INFO.name)
            .assertIsDisplayed()
    }

    private fun ComposeContentTestRule.withoutIcon() = apply {
        onNodeWithTag(SmallWarningViewTags.ICON_INFO.name)
            .assertDoesNotExist()
    }

    private fun ComposeContentTestRule.withActionText(text: String) = apply {
        onNodeWithTag(SmallWarningViewTags.ACTION_TEXT.name)
            .assertTextEquals(text)
            .assertIsDisplayed()
    }

}