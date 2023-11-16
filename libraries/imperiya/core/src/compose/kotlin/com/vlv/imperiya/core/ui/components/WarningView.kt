package com.vlv.imperiya.core.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun WarningView(
    modifier: Modifier = Modifier,
    title: String,
    body: String,
    @DrawableRes
    warningIcon: Int = R.drawable.ic_close_error,
    @ColorRes
    warningIconTint: Int = R.color.color_imperiya_error,
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
                    .layoutId(closeIconId),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier
                .layoutId(titleId),
            text = title,
            style = TheMovieDbTypography.TitleStyle,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            modifier = Modifier
                .layoutId(bodyId),
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
            )
        }

        Icon(
            painter = painterResource(id = warningIcon),
            contentDescription = warningIconDescription,
            modifier = Modifier
                .layoutId(warningIconId)
                .size(96.dp),
            tint = colorResource(id = warningIconTint)
        )
    }


}

@Composable
fun SmallWarningView(
    title: String?,
    body: String?,
    linkActionText: String,
    showIconInfo: Boolean = true,
    onClickLink: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.tertiaryContainer,
                RoundedCornerShape(16.dp)
            )
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        title?.let {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(showIconInfo) {
                    Icon(
                        painter = painterResource(
                            id = R.drawable.ic_info
                        ),
                        contentDescription = null
                    )
                }

                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = if(showIconInfo) 8.dp else 0.dp),
                    style = TheMovieDbTypography.SubTitleBoldStyle,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }

        body?.let {
            Text(
                text = body,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = if(title.isNullOrEmpty()) 0.dp else 12.dp
                    ),
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
        }

        Text(
            text = linkActionText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp
                )
                .clickable {
                    onClickLink?.invoke()
                },
            style = TheMovieDbTypography.LinkStyle
        )
        Spacer(modifier = Modifier.size(16.dp))
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun SmallWarningViewPreview() {
    SmallWarningView(
        "Failure title you put it here",
        "Failure body you pit here",
        "Text button"
    )
}


@Preview(
    uiMode = UI_MODE_NIGHT_YES,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun WarningViewPreview() {
    WarningView(
        modifier = Modifier.fillMaxWidth(),
        title = "Error Title",
        body = "Error body",
        linkActionText = "Try again button text"
    )
}