package com.vlv.imperiya.core.ui.components

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.preview.PreviewLightDarkWithBackground
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

internal enum class WarningViewTags {
    CLOSE_ICON,
    PARENT,
    TITLE,
    BODY,
    BUTTON_LINK,
    WARNING_ICON
}

@Composable
fun WarningView(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    @DrawableRes
    warningIcon: Int = R.drawable.ic_close_error,
    @ColorRes
    warningIconTint: Color = MaterialTheme.colorScheme.error,
    warningIconDescription: String? = null,
    linkActionText: String? = null,
    showCloseIcon: Boolean = true,
    onCloseIcon: (() -> Unit)? = null,
    onClickLink: (() -> Unit)? = null,
) {
    val closeIconId = "close_icon_id"
    val titleId = "title_id"
    val bodyId = "body_id"
    val buttonLinkId = "button_link_id"
    val warningIconId = "warning_icon"

    val constraints = ConstraintSet {
        val closeIconConstraint = createRefFor(closeIconId)
        val titleConstraint = createRefFor(titleId)
        val bodyConstraint = createRefFor(bodyId)
        val buttonLinkConstraint = createRefFor(buttonLinkId)
        val warningIconConstraint = createRefFor(warningIconId)

        if(showCloseIcon) {
            constrain(closeIconConstraint) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                width = Dimension.wrapContent
                height = Dimension.wrapContent
            }
        }

        constrain(titleConstraint) {
            top.linkTo(warningIconConstraint.top)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(warningIconConstraint.start, margin = 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(warningIconConstraint) {
            if(showCloseIcon) {
                top.linkTo(closeIconConstraint.bottom, margin = 24.dp)
            } else {
                top.linkTo(parent.top)
            }
            end.linkTo(parent.end, margin = 16.dp)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }

        constrain(bodyConstraint) {
            top.linkTo(titleConstraint.bottom, margin = 8.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(warningIconConstraint.start, margin = 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }

        constrain(buttonLinkConstraint) {
            top.linkTo(bodyConstraint.bottom, margin = 12.dp)
            start.linkTo(parent.start, margin = 16.dp)
            end.linkTo(warningIconConstraint.start, margin = 16.dp)
            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
    }

    ConstraintLayout(
        constraintSet = constraints,
        modifier = modifier
            .testTag(WarningViewTags.PARENT.name)
    ) {
        if(showCloseIcon) {
            Icon(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = "Close icon",
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        onCloseIcon?.invoke()
                    }
                    .padding(16.dp)
                    .layoutId(closeIconId)
                    .testTag(WarningViewTags.CLOSE_ICON.name),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier
                .layoutId(titleId)
                .testTag(WarningViewTags.TITLE.name),
            text = title,
            style = TheMovieDbTypography.TitleStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .layoutId(bodyId)
                .testTag(WarningViewTags.BODY.name),
            text = body,
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        linkActionText?.let { text ->
            Text(
                text = text,
                style = TheMovieDbTypography.ParagraphBoldStyle,
                color = Link,
                modifier = Modifier
                    .layoutId(buttonLinkId)
                    .clickable {
                        onClickLink?.invoke()
                    }
                    .padding(vertical = 12.dp)
                    .testTag(WarningViewTags.BUTTON_LINK.name)
            )
        }

        Icon(
            painter = painterResource(id = warningIcon),
            contentDescription = warningIconDescription,
            modifier = Modifier
                .layoutId(warningIconId)
                .size(96.dp)
                .testTag(WarningViewTags.WARNING_ICON.name),
            tint = warningIconTint
        )
    }


}


class WarningViewPreviewData(
    val title: String,
    val body: String,
    val linkActionText: String? = null,
)

class WarningViewProvider : PreviewParameterProvider<WarningViewPreviewData> {

    override val values: Sequence<WarningViewPreviewData>
        get() = listOf(
            WarningViewPreviewData(
                "Error Title",
                "Error Body",
                "Try again button text"
            ),
            WarningViewPreviewData(
                "Error Title",
                "Error Body - Without button"
            ),
            WarningViewPreviewData(
                "Error Title - big big big big big",
                "Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.",
                "Try again button text"
            )
        ).asSequence()

}


@PreviewLightDarkWithBackground
@Composable
fun WarningViewPreview(
    @PreviewParameter(WarningViewProvider::class) data: WarningViewPreviewData
) {
    TheMovieDbAppTheme {
        WarningView(
            modifier = Modifier.fillMaxWidth(),
            title = data.title,
            body = data.body,
            linkActionText = data.linkActionText
        )
    }
}


@PreviewFontScale
@Composable
fun WarningViewFontsPreview(
    @PreviewParameter(WarningViewProvider::class) data: WarningViewPreviewData
) {
    TheMovieDbAppTheme(darkTheme = true) {
        WarningView(
            modifier = Modifier.fillMaxWidth(),
            title = data.title,
            body = data.body,
            linkActionText = data.linkActionText
        )
    }
}