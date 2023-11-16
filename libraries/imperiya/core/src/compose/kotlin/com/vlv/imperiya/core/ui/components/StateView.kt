package com.vlv.imperiya.core.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun StateView(
    @DrawableRes icon: Int,
    modifier: Modifier = Modifier,
    title: String? = null,
    body: String? = null,
    contentDescriptionIcon: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescriptionIcon,
            modifier = Modifier
                .size(96.dp),
            tint = MaterialTheme.colorScheme.tertiary
        )
        title?.let {
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = title,
                style = TheMovieDbTypography.TitleStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        body?.let {
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = body,
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun StateViewPreview() {
    TheMovieDbAppTheme() {
        StateView(
            title = "Failure title you put it here",
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable
        )
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun StateViewWithoutTitlePreview() {
    TheMovieDbAppTheme {
        StateView(
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable
        )
    }
}

@Preview(
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun StateViewWithoutBodyPreview() {
    TheMovieDbAppTheme {
        StateView(
            title = "Failure title you put it here",
            icon = R.drawable.ic_heart_enable
        )
    }
}