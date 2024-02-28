package com.vlv.common.ui.about

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun AboutItemInformation(
    item: AboutItem.InformationItem,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = item.information.title),
            modifier = Modifier
                .fillMaxWidth(.5f)
                .padding(end = 8.dp),
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = item.information.data,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun AboutItemInformationPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            AboutItemInformation(
                item = AboutItem.InformationItem(
                    Information(
                        title = R.string.common_error_title,
                        data = "Municipal Pictures, Skydance"
                    )
                )
            )
        }
    }
}