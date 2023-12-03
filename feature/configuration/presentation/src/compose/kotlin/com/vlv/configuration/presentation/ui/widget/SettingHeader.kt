package com.vlv.configuration.presentation.ui.widget

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.configuration.presentation.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingHeader(
    @StringRes
    title: Int
) {
    Text(
        text = stringResource(id = title),
        style = TheMovieDbTypography.TitleStyle,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@PreviewLightDark
@Composable
fun SettingHeader() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingHeader(title = R.string.configuration_language_title)
        }
    }
}
