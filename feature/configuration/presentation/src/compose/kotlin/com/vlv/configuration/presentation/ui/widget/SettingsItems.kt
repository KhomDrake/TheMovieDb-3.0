package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType
import com.vlv.configuration.domain.model.ConfigDataItemList
import com.vlv.configuration.domain.model.ConfigDataList
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

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
                item {
                    when(section.type) {
                        SectionUIType.HEADER -> {
                            SettingHeader(
                                title = section.title.toString(),
                                modifier = Modifier
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
                                onClick = {
                                    sectionDataSelected = it
                                    showBottomSheet = true
                                },
                                modifier = Modifier
                                    .padding(
                                        top = 8.dp
                                    )
                                    .testTag(section.id.toString())
                            )
                        }
                    }
                }
            }
        }
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

class SettingsItemsDataPreview(
    val items: List<SectionUIItem>
)

class SettingsItemsProvider : PreviewParameterProvider<SettingsItemsDataPreview> {
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
fun SettingsItemsPreview(
    @PreviewParameter(SettingsItemsProvider::class) data: SettingsItemsDataPreview
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SettingsItems(
                items = data.items,
                paddingValues = PaddingValues(),
                modifier = Modifier.fillMaxWidth(),
                onConfirmChangeItem = {}
            )
        }
    }
}
