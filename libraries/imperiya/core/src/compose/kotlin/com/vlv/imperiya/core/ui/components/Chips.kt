package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.preview.PreviewLightDarkWithBackground
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Stable
data class FilterItemData(
    val name: String,
    val value: String
)

@Composable
fun FilterGroup(
    filters: List<FilterItemData>,
    onClickFilter: (FilterItemData) -> Unit,
    modifier: Modifier = Modifier,
    selectedFilterItem: FilterItemData? = null,
    contentPaddingValues: PaddingValues = PaddingValues(),
    horizontalArrangement: Arrangement.Horizontal = Arrangement.spacedBy(8.dp)
) {
    LazyRow(
        modifier = modifier,
        content = {
            items(filters) { filter ->
                val selected = selectedFilterItem == filter
                FilterItem(
                    selected = selected,
                    onClickFilter = {
                        if(it != selectedFilterItem) onClickFilter.invoke(filter)
                    },
                    filter = filter
                )
            }
        },
        contentPadding = contentPaddingValues,
        horizontalArrangement = horizontalArrangement
    )
}

@OptIn(ExperimentalMaterial3Api::class)
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
            selectedBorderWidth = 1.dp
        ),
        shape = RoundedCornerShape(16.dp)
    )
}

@PreviewLightDarkWithBackground
@Composable
fun FilterGroupPrev() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            FilterGroup(
                filters = listOf(
                    FilterItemData("Test 11", "asdas"),
                    FilterItemData("Test 22", "asdas"),
                    FilterItemData("Test 33", "asdas"),
                    FilterItemData("Test 44", "asdas"),
                ),
                selectedFilterItem = FilterItemData("Test 33", "asdas"),
                modifier = Modifier.fillMaxWidth(),
                onClickFilter = {

                }
            )
        }
    }
}