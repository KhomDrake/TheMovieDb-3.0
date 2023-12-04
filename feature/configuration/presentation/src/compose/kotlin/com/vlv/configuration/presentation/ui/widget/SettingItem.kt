package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingItem(
    sectionData: SectionUIItem,
    onClick: (SectionUIItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(sectionData)
            }
    ) {
        val data = sectionData.data as ConfigDataList
        var topMargin = 0.dp
        if(sectionData.title != null) {
            topMargin = 8.dp
            Text(
                text = sectionData.title,
                style = TheMovieDbTypography.SubTitleBoldStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if(sectionData.description != null) {
            Text(
                modifier = Modifier
                    .padding(top = topMargin),
                text = "${sectionData.description} ${data.selectedItem.title.toString()}",
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

//class SettingItemProvider: PreviewParameterProvider<SettingsSectionItemsUI> {
//
//    private val default: SettingsSectionItemsUI
//        get() = SettingsSectionItemsUI(
//            SettingsOption.LANGUAGE,
//            type = SectionItemType.LIST,
//            items = listOf(),
//            selectedItem = SettingsItemUI(
//                "abc",
//                R.string.configuration_options_item_backdrop_title
//            ),
//            title = R.string.configuration_options_item_backdrop_title,
//            description = R.string.configuration_options_item_description
//        )
//
//    override val values: Sequence<SettingsSectionItemsUI>
//        get() = listOf<SettingsSectionItemsUI>(
//            default,
//            default.copy(
//                title = -1
//            ),
//            default.copy(
//                description = -1
//            )
//        ).asSequence()
//
//}
//
//@PreviewLightDark
//@Composable
//fun SettingItemPreview(
//    @PreviewParameter(SettingItemProvider::class) data: SettingsSectionItemsUI
//) {
//    TheMovieDbAppTheme {
//        BackgroundPreview {
//            SettingItem(sectionData = data, onClick = {})
//        }
//    }
//}
