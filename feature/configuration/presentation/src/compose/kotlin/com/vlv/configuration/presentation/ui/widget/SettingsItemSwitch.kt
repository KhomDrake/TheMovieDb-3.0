package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.vlv.configuration.data.SectionUIItem
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingsItemSwitch(
    sectionData: SectionUIItem,
    onClick: (SectionUIItem, Boolean) -> Unit
) {
    Row(
        modifier = Modifier
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

//@PreviewLightDark
//@PreviewFontScale
//@Composable
//fun SettingsItemSwitchPreview() {
//    TheMovieDbAppTheme {
//        BackgroundPreview {
//            SettingsItemSwitch(
//                SettingsSectionItemsUI(
//                    option = SettingsOption.LANGUAGE,
//                    type = SectionItemType.SWITCH,
//                    items = listOf(),
//                    selectedItem = SettingsItemUI(
//                        value = true,
//                        nameString = "Title"
//                    ),
//                    description = com.vlv.ui.R.string.common_error_description
//                )
//            ) { _, _ ->
//
//            }
//        }
//    }
//}