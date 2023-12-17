package com.vlv.common.ui.paging

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.SeriesPoster
import com.vlv.common.ui.extension.isFullLoading
import com.vlv.common.ui.extension.isSingleError
import com.vlv.common.ui.extension.isSingleLoading
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePosterShimmer

@Composable
fun SeriesPagingGrid(
    modifier: Modifier = Modifier,
    heightItem: Dp = 200.dp,
    itemCountInitialLoading: Int = 4,
    columns: Int = 2,
    seriesItems: LazyPagingItems<Series>,
    routeNavigation: RouteNavigation
) {
    if(seriesItems.loadState.isFullLoading()) {
        GridPosterShimmer(
            modifier = modifier,
            count = itemCountInitialLoading,
            height = heightItem
        )
    } else {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(columns),
            content = {
                items(
                    count = seriesItems.itemCount,
                    key = seriesItems.itemKey { movie -> movie.id },
                    contentType = seriesItems.itemContentType { "Poster" }
                ) { index ->
                    seriesItems[index]?.let { series ->
                        SeriesPoster(
                            series = series,
                            height = heightItem,
                            onRouteNavigation = routeNavigation
                        )
                    }
                }

                item {

                    when {
                        seriesItems.loadState.isSingleLoading() -> {
                            SinglePosterShimmer(
                                modifier = Modifier.fillMaxWidth(),
                                height = 200.dp
                            )
                        }
                        seriesItems.loadState.isSingleError() -> {
                            // one item error
                        }
                        else -> Unit
                    }
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