package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionItemType
import com.vlv.configuration.data.SettingsItemUI
import com.vlv.configuration.data.SettingsSectionItemsUI
import com.vlv.configuration.domain.model.SettingsOption
import com.vlv.configuration.presentation.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import java.lang.reflect.Parameter

@Composable
fun SettingItem(
    sectionData: SettingsSectionItemsUI,
    onClick: (SettingsSectionItemsUI) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(sectionData)
            }
    ) {
        var topMargin = 0.dp
        if(sectionData.title != -1) {
            topMargin = 8.dp
            Text(
                text = stringResource(id = sectionData.title),
                style = TheMovieDbTypography.SubTitleBoldStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        if(sectionData.description != -1) {
            Text(
                modifier = Modifier
                    .padding(top = topMargin),
                text = stringResource(
                    id = sectionData.description,
                    sectionData.selectedItem.nameString.ifEmpty {
                        sectionData.selectedItem.value as String
                    }
                ),
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

class SettingItemProvider: PreviewParameterProvider<SettingsSectionItemsUI> {

    private val default: SettingsSectionItemsUI
        get() = SettingsSectionItemsUI(
            SettingsOption.LANGUAGE,
            type = SectionItemType.LIST,
            items = listOf(),
            selectedItem = SettingsItemUI(
                "abc",
                R.string.configuration_options_item_backdrop_title
            ),
            title = R.string.configuration_options_item_backdrop_title,
            description = R.string.configuration_options_item_description
        )

    override val values: Sequence<SettingsSectionItemsUI>
        get() = listOf<SettingsSectionItemsUI>(
            default,
            default.copy(
                title = -1
            ),
            default.copy(
                description = -1
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun SettingItemPreview(
    @PreviewParameter(SettingItemProvider::class) data: SettingsSectionItemsUI
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingItem(sectionData = data, onClick = {})
        }
    }
}
