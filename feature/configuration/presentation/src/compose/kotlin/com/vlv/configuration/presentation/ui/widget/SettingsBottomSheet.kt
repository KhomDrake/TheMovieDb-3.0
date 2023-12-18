package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.imperiya.core.ui.components.TheMovieDbModalBottomSheet
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBottomSheet(
    data: SectionUIItem,
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onClick: (ConfigDataList, ConfigDataItemList) -> Unit,
    modifier: Modifier = Modifier
) {
    TheMovieDbModalBottomSheet(
        modifier = modifier,
        onDismissRequest = {
            onDismiss.invoke()
        },
        sheetState = sheetState,
        content = {
            val configDataList = data.data as ConfigDataList
            Text(
                text = data.title.toString(),
                style = TheMovieDbTypography.SubTitleBoldStyle,
                color = MaterialTheme.colorScheme.onBackground
            )

            BottomSheetContent(data = configDataList, onClick = { configDataItemList ->
                onClick.invoke(configDataList, configDataItemList)
            })
        }
    )
}

@Composable
fun BottomSheetContent(
    data: ConfigDataList,
    onClick: (ConfigDataItemList) -> Unit
) {
    var selectedItem by remember { mutableStateOf(data.selectedItem) }
    val first by remember { mutableStateOf(data.selectedItem) }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 12.dp),
        content = {
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
            item {
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
                        text = "CONFIRM",
                        style = TheMovieDbTypography.SubTitleBoldStyle,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .testTag("BottomSheetButton")
                    )
                }
            }
        }
    )
}

@Composable
fun BottomSheetItem(
    selectedItem: ConfigDataItemList,
    item: ConfigDataItemList,
    onCheckedChange: (ConfigDataItemList) -> Unit,
    modifier: Modifier = Modifier
) {
    val text = item.title.toString()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onCheckedChange.invoke(item)
            },
        verticalAlignment = CenterVertically
    ) {
        Text(
            text = text,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            modifier = Modifier
                .weight(10f)
                .padding(end = 12.dp),
            color = MaterialTheme.colorScheme.tertiary
        )
        Checkbox(
            selectedItem.id == item.id,
            modifier = Modifier
                .weight(1f),
            onCheckedChange = {
                onCheckedChange.invoke(item)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

//class BottomSheetItemPreviewData(
//    val item: SettingsItemUI,
//    val selectedItem: SettingsItemUI
//)
//
//class BottomSheetItemProvider: PreviewParameterProvider<BottomSheetItemPreviewData> {
//    override val values: Sequence<BottomSheetItemPreviewData>
//        get() = listOf(
//            BottomSheetItemPreviewData(
//                SettingsItemUI(
//                    "abc",
//                    R.string.configuration_options_item_backdrop_title,
//                    id = 5
//                ),
//                SettingsItemUI(
//                    "abc",
//                    R.string.configuration_options_item_backdrop_title,
//                    id = 3
//                )
//            ),
//            BottomSheetItemPreviewData(
//                SettingsItemUI(
//                    "abc",
//                    R.string.configuration_options_item_backdrop_title,
//                    id = 2
//                ),
//                SettingsItemUI(
//                    "abc",
//                    R.string.configuration_options_item_backdrop_title,
//                    id = 2
//                )
//            )
//        ).asSequence()
//}
//
//@PreviewLightDark
//@Composable
//fun BottomSheetItemPreview(
//    @PreviewParameter(BottomSheetItemProvider::class) data: BottomSheetItemPreviewData
//) {
//    TheMovieDbAppTheme {
//        BackgroundPreview {
//            BottomSheetItem(
//                selectedItem = data.selectedItem,
//                item = data.item,
//                onCheckedChange = {
//
//                })
//        }
//    }
//}
//
//@PreviewLightDark
//@Composable
//fun SettingsBottomSheetContentPreview() {
//    TheMovieDbAppTheme {
//        BackgroundPreview {
//            BottomSheetContent(
//                data = SettingsSectionItemsUI(
//                    SettingsOption.LANGUAGE,
//                    type = SectionItemType.LIST,
//                    items = SettingsOption.values().map {
//                        SettingsItemUI(
//                            it.name,
//                            nameString = it.name
//                        )
//                    },
//                    selectedItem = SettingsItemUI(
//                        "abc",
//                        R.string.configuration_options_item_backdrop_title
//                    ),
//                    title = R.string.configuration_options_item_backdrop_title,
//                    description = R.string.configuration_options_item_description
//                ),
//                onClick = { _, _ -> }
//            )
//        }
//    }
//}