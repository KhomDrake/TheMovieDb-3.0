package com.vlv.configuration.presentation.ui.widget.bottomsheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import br.com.khomdrake.imperiya.ui.preview.BackgroundPreview
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTheme
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTypography
import com.vlv.configuration.domain.model.ConfigDataItemList

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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            style = ImperiyaTypography.SubTitleBoldStyle,
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

class BottomSheetItemPreviewData(
    val selectedItem: ConfigDataItemList,
    val item: ConfigDataItemList
)

class BottomSheetItemPreviewProvider: PreviewParameterProvider<BottomSheetItemPreviewData> {

    override val values: Sequence<BottomSheetItemPreviewData>
        get() = listOf(
            BottomSheetItemPreviewData(
                selectedItem = ConfigDataItemList(
                    "German",
                    "title",
                    id = 2
                ),
                item = ConfigDataItemList(
                    "German",
                    "title",
                    id = 2
                )
            ),
            BottomSheetItemPreviewData(
                selectedItem = ConfigDataItemList(
                    "German",
                    "title"
                ),
                item = ConfigDataItemList(
                    "Portuguese",
                    "title"
                )
            ),
        ).asSequence()

}

@PreviewLightDark
@Composable
fun BottomSheetItemPreview(
    @PreviewParameter(BottomSheetItemPreviewProvider::class) data: BottomSheetItemPreviewData
) {
    ImperiyaTheme {
        BackgroundPreview {
            BottomSheetItem(
                selectedItem = data.selectedItem,
                item = data.item,
                onCheckedChange = {

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
