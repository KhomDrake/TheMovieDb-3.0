package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SettingsSectionItemsUI
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
                .padding(top = 8.dp),
            text = stringResource(
                id = sectionData.description
            ),
            style = TheMovieDbTypography.ParagraphStyle,
            color = MaterialTheme.colorScheme.onBackground
        )
        Switch(
            checked = sectionData.selectedItem.value as Boolean,
            onCheckedChange = {
                onClick.invoke(sectionData)
            }
        )
    }
}