package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    itemCount: Int,
    currentIndex: Int,
    onClick: (Int) -> Unit,
    item: @Composable (Int, Boolean) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentIndex,
        edgePadding = 16.dp,
        divider = {},
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .tabIndicatorOffset(tabPositions[currentIndex]),
                height = 2.dp,
                color = Link
            )
        }
    ) {
        for (index in 0 until itemCount) {
            Tab(
                selected = index == currentIndex,
                onClick = { onClick.invoke(index) },
                selectedContentColor = Link,
                unselectedContentColor = MaterialTheme.colorScheme.onBackground,
                content = {
                    item.invoke(index, index == currentIndex)
                }
            )
        }
    }
}

@Composable
fun TabItem(
    name: String,
    isSelected: Boolean
) {
    Text(
        modifier = Modifier
            .padding(16.dp),
        text = name,
        style = TheMovieDbTypography.TitleStyle,
        color = if (isSelected) Link else MaterialTheme.colorScheme.onBackground
    )
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun TabPreview() {
    TheMovieDbAppTheme {
        val items = listOf("Text1", "Text2", "Text3")

        TabRow(
            itemCount = items.size,
            currentIndex = 0,
            onClick = {

            }
        ) { index, isSelected ->
            TabItem(name = items[index], isSelected = isSelected)
        }
    }
}