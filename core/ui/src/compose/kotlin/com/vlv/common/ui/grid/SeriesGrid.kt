package com.vlv.common.ui.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.poster.SeriesPoster
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun SeriesGrid(
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

@PreviewLightDark
@Composable
fun SeriesGridPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeriesGrid(
                series = listOf(
                    Series(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    Series(
                        false,
                        3,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 2"
                    ),
                    Series(
                        false,
                        4,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 3"
                    ),
                    Series(
                        false,
                        5,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 4"
                    )
                ),
                routeNavigation = {_, _ ->},
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
