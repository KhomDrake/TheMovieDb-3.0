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