package com.vlv.configuration.presentation.ui.widget.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.khomdrake.imperiya.ui.preview.BackgroundPreview
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTheme
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTypography
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.presentation.R

private const val TITLE_KEY = "TITLE_KEY"
private const val BUTTON_KEY = "BUTTON_KEY"

@Composable
fun BottomSheetContent(
    data: ConfigDataList,
    onClick: (ConfigDataItemList) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf(data.selectedItem) }
    val first by remember { mutableStateOf(data.selectedItem) }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        content = {
            item(key = TITLE_KEY) {
                Text(
                    text = data.title.toString(),
                    style = ImperiyaTypography.SubTitleBoldStyle,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            data.items.forEach { item ->
                item {
                    BottomSheetItem(
                        selectedItem = selectedItem,
                        item = item,
                        onCheckedChange = {
                            selectedItem = it
                        },
                        modifier = Modifier
                            .testTag(item.id.toString())
                    )
                }
            }
            item(key = BUTTON_KEY) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        onClick.invoke(selectedItem)
                    },
                    enabled = first.id != selectedItem.id,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        disabledContainerColor = MaterialTheme.colorScheme.outline,
                        disabledContentColor = MaterialTheme.colorScheme.outlineVariant,
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.configuration_button),
                        style = ImperiyaTypography.SubTitleBoldStyle,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .testTag("BottomSheetButton")
                    )
                }
            }
        },
        verticalArrangement = Arrangement.spacedBy(12.dp)
    )

}

@PreviewLightDark
@Composable
fun BottomSheetContentPreview() {
    ImperiyaTheme {
        BackgroundPreview {
            val items = listOf(
                ConfigDataItemList(
                    "Lowest",
                    "Lowest"
                ),
                ConfigDataItemList(
                    "Low",
                    "Lowest"
                ),
                ConfigDataItemList(
                    "Medium low",
                    "Lowest"
                ),
                ConfigDataItemList(
                    "Medium",
                    "Lowest"
                ),
                ConfigDataItemList(
                    "High",
                    "Lowest"
                ),
            )
            BottomSheetContent(
                data = ConfigDataList(
                    title = "Logo image definition",
                    description = "",
                    selectedItem = items[3],
                    items = items
                ),
                onClick = {

                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
