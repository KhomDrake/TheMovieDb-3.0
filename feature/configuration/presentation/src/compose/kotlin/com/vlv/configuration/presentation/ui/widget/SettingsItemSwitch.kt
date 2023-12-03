package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionItemType
import com.vlv.configuration.data.SettingsItemUI
import com.vlv.configuration.data.SettingsSectionItemsUI
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.configuration.presentation.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingsItemSwitch(
    sectionData: SettingsSectionItemsUI,
    onClick: (SettingsSectionItemsUI) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .weight(4f)
                .padding(top = 8.dp),
            text = stringResource(
                id = sectionData.description
            ),
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
        Switch(
            modifier = Modifier
                .weight(1f),
            checked = sectionData.selectedItem.value as Boolean,
            onCheckedChange = {
                onClick.invoke(sectionData)
            }
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun SettingsItemSwitchPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsItemSwitch(
                SettingsSectionItemsUI(
                    option = SettingsOption.LANGUAGE,
                    type = SectionItemType.SWITCH,
                    items = listOf(),
                    selectedItem = SettingsItemUI(
                        value = true,
                        nameString = "Title"
                    ),
                    description = R.string.configuration_options_item_backdrop_title
                )
            ) {

            }
        }
    }
}