package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.domain.model.SettingOption
import com.vlv.extensions.idInt
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun SettingItem(
    sectionData: SectionUIItem,
    onClick: (SectionUIItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable {
                onClick.invoke(sectionData)
            }
    ) {
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
                text = sectionData.descriptionWithSelectedValue,
                style = TheMovieDbTypography.ParagraphStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

class SettingItemProvider: PreviewParameterProvider<SectionUIItem> {

    private val default: SectionUIItem
        get() = SectionUIItem(
            type = SectionUIType.HEADER,
            id = idInt(),
            data = ConfigDataList(
                "a",
                "b",
                ConfigDataItemList(
                    "Value",
                    ""
                ),
                listOf()
            ),
            title = "Normal title",
            description = "Normal description",
            settingsOption = SettingOption.POSTER
        )

    override val values: Sequence<SectionUIItem>
        get() = listOf(
            default,
            default.copy(
                title = null
            ),
            default.copy(
                description = null
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun SettingItemPreview(
    @PreviewParameter(SettingItemProvider::class) data: SectionUIItem
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingItem(
                sectionData = data,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
