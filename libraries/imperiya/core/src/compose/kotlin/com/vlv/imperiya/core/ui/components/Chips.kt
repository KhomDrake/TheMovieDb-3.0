package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Stable
data class FilterItemData(
    val id: Int,
    val name: String,
    val value: String
)

@Composable
fun FilterGroup(
    filters: List<FilterItemData>,
    onClickFilter: (Int, FilterItemData) -> Unit,
    modifier: Modifier = Modifier,
    selectedFilterItem: Int? = null,
    contentPaddingValues: PaddingValues = PaddingValues(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    LazyRow(
        modifier = modifier,
        content = {
            itemsIndexed(
                filters,
                key = { _, item -> item.id }
            ) {index, filter ->
                val selected = selectedFilterItem == index
                FilterItem(
                    selected = selected,
                    onClickFilter = {
                        if(index != selectedFilterItem) onClickFilter.invoke(index, filter)
                    },
                    filter = filter
                )
            }
        },
        contentPadding = contentPaddingValues,
        horizontalArrangement = horizontalArrangement
    )
}

@Composable
fun FilterItem(
    selected: Boolean,
    filter: FilterItemData,
    onClickFilter: (FilterItemData) -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        modifier = modifier,
        selected = selected,
        onClick = {
            onClickFilter.invoke(filter)
        },
        label = {
            Text(
                text = filter.name,
                style = TheMovieDbTypography.ParagraphBoldStyle,
                color = if(selected) MaterialTheme.colorScheme.onSecondary
                    else MaterialTheme.colorScheme.onBackground
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.secondary,
            containerColor = MaterialTheme.colorScheme.background
        ),
        border = FilterChipDefaults.filterChipBorder(
            borderColor = MaterialTheme.colorScheme.outlineVariant,
            selectedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            selectedBorderWidth = 1.dp,
            enabled = true,
            selected = selected
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@PreviewLightDark
@Composable
fun FilterGroupPrev() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            FilterGroup(
                filters = listOf(
                    FilterItemData(1, "Test 11", "asdas"),
                    FilterItemData(2, "Test 22", "asdas"),
                    FilterItemData(3, "Test 33", "asdas"),
                    FilterItemData(4, "Test 44", "asdas"),
                ),
                selectedFilterItem = 2,
                modifier = Modifier.fillMaxWidth(),
                onClickFilter = { _, _ ->

                }
            )
        }
    }
}