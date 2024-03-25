package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import br.com.khomdrake.imperiya.ui.preview.BackgroundPreview
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTheme
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.configuration.presentation.ui.widget.bottomsheet.SettingsBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItems(
    items: List<SectionUIItem>,
    paddingValues: PaddingValues,
    onConfirmChangeItem: (type: SectionUIItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sectionDataSelected: SectionUIItem? by remember { mutableStateOf(null) }

    SettingsItemList(
        items = items,
        paddingValues = paddingValues,
        onConfirmChangeItem = onConfirmChangeItem,
        onClickItem = {
            sectionDataSelected = it
            showBottomSheet = true
        },
        modifier = modifier
    )

    if(showBottomSheet) {
        val data = sectionDataSelected ?: return
        SettingsBottomSheet(
            data = data,
            sheetState = sheetState,
            onDismiss = {
                showBottomSheet = false
                sectionDataSelected = null
            },
            onClick = { configDataList, selectedItemDataList ->
                showBottomSheet = false
                onConfirmChangeItem.invoke(
                    data.copy(
                        data = configDataList.copy(
                            selectedItem = selectedItemDataList
                        )
                    )
                )
            },
            modifier = Modifier
                .testTag("SettingsBottomSheet")
        )
    }
}

@Composable
fun SettingsItemList(
    paddingValues: PaddingValues,
    items: List<SectionUIItem>,
    onConfirmChangeItem: (type: SectionUIItem) -> Unit,
    onClickItem: (SectionUIItem) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp
            ),
        contentPadding = PaddingValues(
            vertical = 12.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        content = {
            items.forEach { section ->
                item(key = section.id) {
                    when(section.type) {
                        SectionUIType.HEADER -> {
                            SettingHeader(
                                title = section.title.toString(),
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp
                                    )
                                    .testTag(section.id.toString())
                            )
                        }
                        SectionUIType.SWITCH -> {
                            SettingsItemSwitch(
                                sectionData = section,
                                onClick = { data, value ->
                                    onConfirmChangeItem.invoke(
                                        data.copy(
                                            data = value
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .testTag(section.id.toString())
                            )
                        }
                        SectionUIType.LIST -> {
                            SettingItem(
                                sectionData = section,
                                onClick = onClickItem,
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp
                                    )
                                    .fillMaxWidth()
                                    .testTag(section.id.toString())
                            )
                        }
                    }
                }
            }
        }
    )
}

class SettingsItemsDataPreview(
    val items: List<SectionUIItem>
)

class SettingsItemsListProvider : PreviewParameterProvider<SettingsItemsDataPreview> {
    override val values: Sequence<SettingsItemsDataPreview>
        get() = listOf(
            SettingsItemsDataPreview(
                items = listOf(
                    SectionUIItem(
                        type = SectionUIType.HEADER,
                        id = 23,
                        data = null,
                        title = "General"
                    ),
                    SectionUIItem(
                        type = SectionUIType.SWITCH,
                        id = 24,
                        data = true,
                        title = null,
                        description = "Show adult content"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 25,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "us",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Languages",
                        description = "Language chosen:"
                    )
                )
            ),
            SettingsItemsDataPreview(
                items = listOf(
                    SectionUIItem(
                        type = SectionUIType.HEADER,
                        id = 23,
                        data = null,
                        title = "General"
                    ),
                    SectionUIItem(
                        type = SectionUIType.SWITCH,
                        id = 24,
                        data = true,
                        title = null,
                        description = "Show adult content"
                    )
                )
            ),
            SettingsItemsDataPreview(
                items = listOf(
                    SectionUIItem(
                        type = SectionUIType.HEADER,
                        id = 23,
                        data = null,
                        title = "General"
                    ),
                    SectionUIItem(
                        type = SectionUIType.SWITCH,
                        id = 20,
                        data = true,
                        title = null,
                        description = "Show adult content"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 19,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "us",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Languages",
                        description = "Language chosen:"
                    ),
                    SectionUIItem(
                        type = SectionUIType.HEADER,
                        id = 21,
                        data = null,
                        title = "Image Definition"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 24,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "High",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Backdrop image definition",
                        description = "Definition chosen:"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 25,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "Medium",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Logo image definition",
                        description = "Definition chosen:"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 26,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "Highest",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Poster image definition",
                        description = "Definition chosen:"
                    ),
                    SectionUIItem(
                        type = SectionUIType.LIST,
                        id = 27,
                        data = ConfigDataList(
                            title = "Lan",
                            description = "Dlaskd",
                            selectedItem = ConfigDataItemList(
                                "Low",
                                "klsjda"
                            ),
                            listOf()
                        ),
                        title = "Profile image definition",
                        description = "Definition chosen:"
                    ),

                )
            )
        ).asSequence()
}

@PreviewLightDark
@Composable
fun SettingsItemsListPreview(
    @PreviewParameter(SettingsItemsListProvider::class) data: SettingsItemsDataPreview
) {
    ImperiyaTheme {
        BackgroundPreview {
            SettingsItemList(
                items = data.items,
                paddingValues = PaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                onConfirmChangeItem = {},
                onClickItem = {}
            )
        }
    }
}
