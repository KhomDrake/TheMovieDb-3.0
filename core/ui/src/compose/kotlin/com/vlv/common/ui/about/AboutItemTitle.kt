package com.vlv.common.ui.about

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun AboutItemTitle(
    item: AboutItem.Title,
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = item.text),
        color = MaterialTheme.colorScheme.onBackground,
        style = TheMovieDbTypography.TitleStyle,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun AboutItemTitlePreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutItemTitle(
                item = AboutItem.Title(R.string.common_error_title),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}