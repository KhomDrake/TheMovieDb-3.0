package com.vlv.configuration.presentation.ui.widget

import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
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