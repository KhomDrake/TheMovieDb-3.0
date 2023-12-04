package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionUIItem
import com.vlv.configuration.data.SectionUIType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItems(
    items: List<SectionUIItem>,
    paddingValues: PaddingValues,
    onConfirmChangeItem: (type: SectionUIItem) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sectionDataSelected: SectionUIItem? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = Modifier
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
                            SettingHeader(title = section.title.toString())
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
                                }
                            )
                        }
                        SectionUIType.LIST -> {
                            SettingItem(
                                sectionData = section,
                                onClick = {
                                    sectionDataSelected = it
                                    showBottomSheet = true
                                }
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
            }
        )
    }
}
