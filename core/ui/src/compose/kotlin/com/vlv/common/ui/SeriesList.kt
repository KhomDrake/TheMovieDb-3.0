package com.vlv.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation

@Composable
fun SeriesList(
    modifier: Modifier = Modifier,
    heightItem: Dp = 200.dp,
    columns: Int = 2,
    series: List<Series>,
    routeNavigation: RouteNavigation
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        content = {
            items(series) { series ->
                SeriesPoster(
                    series = series,
                    height = heightItem,
                    onRouteNavigation = routeNavigation
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
}