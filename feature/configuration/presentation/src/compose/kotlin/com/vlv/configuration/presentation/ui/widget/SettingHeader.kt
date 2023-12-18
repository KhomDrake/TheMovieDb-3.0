package com.vlv.configuration.presentation.ui.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.configuration.presentation.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = TheMovieDbTypography.TitleStyle,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun SettingHeaderPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingHeader(title = stringResource(id = R.string.configuration_toolbar_title))
        }
    }
}
