package com.vlv.configuration.presentation.ui.widget.bottomsheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.imperiya.core.ui.components.TheMovieDbModalBottomSheet

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
            BottomSheetContent(
                data = configDataList,
                onClick = { configDataItemList ->
                    onClick.invoke(configDataList, configDataItemList)
                }
            )
        }
    )
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