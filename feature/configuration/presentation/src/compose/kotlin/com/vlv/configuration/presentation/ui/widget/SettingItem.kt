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
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SettingsSectionItemsUI
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

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