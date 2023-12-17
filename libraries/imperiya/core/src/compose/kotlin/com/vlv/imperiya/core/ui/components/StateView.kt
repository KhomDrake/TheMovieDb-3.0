package com.vlv.imperiya.core.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.preview.PreviewLightDarkWithBackground
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

internal enum class StateViewTags {
    ICON,
    TITLE,
    BODY,
    PARENT
}

@Composable
fun StateView(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    title: String? = null,
    body: String? = null,
    contentDescriptionIcon: String? = null,
    iconTint: Color = MaterialTheme.colorScheme.tertiary
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = 16.dp
            )
            .testTag(StateViewTags.PARENT.name),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescriptionIcon,
            modifier = Modifier
                .size(96.dp)
                .testTag(StateViewTags.ICON.name),
            tint = iconTint
        )
        title?.let {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = title,
                style = TheMovieDbTypography.TitleStyle,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .testTag(StateViewTags.TITLE.name)
            )
        }

        body?.let {
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = body,
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .testTag(StateViewTags.BODY.name)
            )
        }
    }
}

@PreviewLightDarkWithBackground
@Composable
fun StateViewPreview() {
    TheMovieDbAppTheme {
        StateView(
            title = "Failure title you put it here",
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable,
            iconTint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewFontScale
@Composable
fun StateViewFontScalePreview() {
    TheMovieDbAppTheme(darkTheme = true) {
        StateView(
            title = "Failure title you put it here",
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable,
            iconTint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDarkWithBackground
@Composable
fun StateViewWithoutTitlePreview() {
    TheMovieDbAppTheme {
        StateView(
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable,
            iconTint = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDarkWithBackground
@Composable
fun StateViewWithoutBodyPreview() {
    TheMovieDbAppTheme {
        StateView(
            title = "Failure title you put it here",
            icon = R.drawable.ic_heart_enable,
            iconTint = MaterialTheme.colorScheme.onBackground
        )
    }
}