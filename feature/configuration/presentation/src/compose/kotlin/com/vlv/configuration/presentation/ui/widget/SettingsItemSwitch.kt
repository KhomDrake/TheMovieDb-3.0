package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingsItemSwitch(
    sectionData: SectionUIItem,
    onClick: (SectionUIItem, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        if(sectionData.title != null) {
            Text(
                text = sectionData.title,
                style = TheMovieDbTypography.SubTitleBoldStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .weight(4f),
                text = sectionData.description.toString(),
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
            Switch(
                modifier = Modifier
                    .weight(1f),
                checked = sectionData.data as Boolean,
                onCheckedChange = {
                    onClick.invoke(sectionData, it)
                }
            )
        }
    }

}

@PreviewLightDark
@PreviewFontScale
@Composable
fun SettingsItemSwitchPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsItemSwitch(
                sectionData = SectionUIItem(
                    type = SectionUIType.SWITCH,
                    id = 2,
                    data = true,
                    title = "Use dynamic colors",
                    description = "If enabled, the app will use the colors defined by the user's device"
                ),
                onClick = { _, _ ->

                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}