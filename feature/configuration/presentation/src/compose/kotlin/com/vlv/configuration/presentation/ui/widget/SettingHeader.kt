package com.vlv.configuration.presentation.ui.widget

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.khomdrake.imperiya.ui.preview.BackgroundPreview
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTheme
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTypography
import com.vlv.configuration.presentation.R

@Composable
fun SettingHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = ImperiyaTypography.TitleStyle,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun SettingHeaderPreview() {
    ImperiyaTheme {
        BackgroundPreview {
            SettingHeader(title = stringResource(id = R.string.configuration_toolbar_title))
        }
    }
}
