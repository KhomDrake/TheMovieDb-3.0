package com.vlv.common.ui.about

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.common.data.about.AboutItem
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun AboutItemBigText(
    item: AboutItem.BigText,
    modifier: Modifier = Modifier
) {
    Text(
        text = item.text,
        style = TheMovieDbTypography.ParagraphStyle,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}


@PreviewLightDark
@PreviewFontScale
@Composable
fun AboutItemBigTextPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutItemBigText(item = AboutItem.BigText(
                null,
                "Dan Morgan is many things: a devoted husband, a loving father, a celebrated car salesman. He's also a former assassin. And when his past catches up to his present, he's forced to take his unsuspecting family on a road trip unlike any other."
            ))
        }
    }
}