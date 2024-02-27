package com.vlv.common.ui.grid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlv.common.data.series.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.series.TvShowsEmptyState
import com.vlv.common.ui.poster.TvShowsPoster
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun TvShowsGrid(
    tvShows: List<TvShow>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    heightItem: Dp = 200.dp,
    columns: Int = 2,
    emptyStateTitle: String = stringResource(id = R.string.common_empty_view_title_default)
) {
    if(tvShows.isEmpty()) {
        TvShowsEmptyState(
            title = emptyStateTitle,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(columns),
            content = {
                items(
                    tvShows,
                    key = { item -> item.id }
                ) { series ->
                    TvShowsPoster(
                        tvShow = series,
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
}

class SeriesGridPreviewProvider: PreviewParameterProvider<List<TvShow>> {

    override val values: Sequence<List<TvShow>>
        get() = listOf(
            listOf(),
            listOf(
                TvShow(
                    false,
                    2,
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "Duna"
                ),
                TvShow(
                    false,
                    3,
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "Duna 2"
                ),
                TvShow(
                    false,
                    4,
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "Duna 3"
                ),
                TvShow(
                    false,
                    5,
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "Duna 4"
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun TvShowsGridPreview(
    @PreviewParameter(SeriesGridPreviewProvider::class) data: List<TvShow>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsGrid(
                tvShows = data,
                routeNavigation = {_, _ ->},
                modifier = Modifier
                    .fillMaxSize(),
                emptyStateTitle = "None series was found"
            )
        }
    }
}
