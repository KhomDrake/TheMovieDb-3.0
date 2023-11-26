package com.vlv.configuration.presentation.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.configuration.data.SectionItemType
import com.vlv.configuration.data.SettingsDataUI
import com.vlv.configuration.data.SettingsSectionItemsUI
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItems(
    settingsDataUI: SettingsDataUI,
    paddingValues: PaddingValues,
    onConfirmChangeItem: (sectionData: Int, item: Int) -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var sectionDataSelected: SettingsSectionItemsUI? by remember { mutableStateOf(null) }

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
            settingsDataUI.sections.forEach { section ->
                item {
                    SettingHeader(title = section.sectionTitle)
                }
                section.sectionData.forEach { sectionData ->
                    item {
                        when(sectionData.type) {
                            SectionItemType.SWITCH -> {
                                SettingsItemSwitch(
                                    sectionData = sectionData,
                                    onClick = {
                                        sectionDataSelected = it
                                        showBottomSheet = true
                                    }
                                )
                            }
                            else -> {
                                SettingItem(
                                    sectionData = sectionData,
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
        }
    )

    if(showBottomSheet) {
        val data = sectionDataSelected ?: return
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = {
                showBottomSheet = false
                sectionDataSelected = null
            },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.background,
            // Temporary solution
            windowInsets = WindowInsets(
                bottom = paddingValues.calculateTopPadding()
            )
        ) {
            var selectedItem by remember { mutableStateOf(data.selectedItem) }
            val first by remember { mutableStateOf(data.selectedItem) }

            Column(
                modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp
                    )
            ) {
                Text(
                    text = stringResource(id = data.title),
                    style = TheMovieDbTypography.TitleStyle,
                    color = MaterialTheme.colorScheme.onBackground
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth(),
                    content = {
                        data.items.forEach { item ->
                            item {
                                val text = item.nameString.ifEmpty {
                                    stringResource(id = item.name)
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = text,
                                        style = TheMovieDbTypography.SubTitleBoldStyle,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(end = 12.dp),
                                        color = MaterialTheme.colorScheme.tertiary
                                    )
                                    Checkbox(
                                        selectedItem.id == item.id,
                                        onCheckedChange = {
                                            selectedItem = item
                                        },
                                        colors = CheckboxDefaults.colors(
                                            checkedColor = MaterialTheme.colorScheme.primary,
                                            uncheckedColor = MaterialTheme.colorScheme.outline
                                        )
                                    )
                                }
                            }
                        }
                        item {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    scope.launch {
                                        showBottomSheet = false
                                        onConfirmChangeItem.invoke(
                                            data.id, selectedItem.id
                                        )
                                    }
                                },
                                enabled = first.id != selectedItem.id,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.tertiary
                                )
                            ) {
                                Text(
                                    text = "CONFIRM",
                                    style = TheMovieDbTypography.SubTitleBoldStyle,
                                    color = MaterialTheme.colorScheme.onTertiary
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}