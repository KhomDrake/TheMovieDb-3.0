package com.vlv.common.ui.paging.series

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.isFullError
import com.vlv.common.ui.extension.isFullLoading
import com.vlv.common.ui.extension.isSingleError
import com.vlv.common.ui.extension.isSingleLoading
import com.vlv.common.ui.extension.stateData
import com.vlv.common.ui.extension.stateFullError
import com.vlv.common.ui.extension.stateFullLoading
import com.vlv.common.ui.extension.stateSingleError
import com.vlv.common.ui.extension.stateSingleLoading
import com.vlv.common.ui.poster.TvShowsPoster
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePosterShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

const val TV_SHOW_CONTENT_TYPE = "SERIES_CONTENT_TYPE"

@Composable
fun TvShowsPagingGrid(
    item: (Int) -> TvShow?,
    loadStates: CombinedLoadStates,
    itemCount: Int,
    routeNavigation: RouteNavigation,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    heightItem: Dp = 200.dp,
    itemCountInitialLoading: Int = 4,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    columns: Int = 2,
    errorPaddingValues: PaddingValues = PaddingValues(16.dp),
    emptyState: @Composable () -> Unit = {
         TvShowsEmptyState(
             title = stringResource(
                 id = R.string.common_series_empty_view_title_default
             ),
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp),
         )
    },
) {
    when {
        loadStates.isFullLoading() -> {
            GridPosterShimmer(
                modifier = modifier,
                count = itemCountInitialLoading,
                height = heightItem
            )
        }
        loadStates.isFullError() -> {
            TvShowsErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(errorPaddingValues),
                onTryAgain = {
                    onRetry.invoke()
                }
            )
        }
        else -> {
            if(itemCount == 0) {
                emptyState.invoke()
            } else {
                LazyVerticalGrid(
                    modifier = modifier,
                    columns = GridCells.Fixed(columns),
                    content = {
                        items(
                            count = itemCount,
                            key = itemKey,
                            contentType = itemContentType
                        ) { index ->
                            item.invoke(index)?.let { series ->
                                TvShowsPoster(
                                    tvShow = series,
                                    height = heightItem,
                                    onRouteNavigation = routeNavigation
                                )
                            }
                        }

                        when {
                            loadStates.isSingleLoading() -> {
                                item(
                                    key = ResponseStatus.LOADING,
                                    contentType = ResponseStatus.LOADING
                                ) {
                                    SinglePosterShimmer(
                                        modifier = Modifier.fillMaxWidth(),
                                        height = heightItem
                                    )
                                }
                            }
                            loadStates.isSingleError() -> {
                                item(
                                    key = ResponseStatus.ERROR,
                                    contentType = ResponseStatus.ERROR,
                                    span = {
                                        GridItemSpan(maxLineSpan)
                                    }
                                ) {
                                    TvShowsErrorState(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 8.dp),
                                        onTryAgain = {
                                            onRetry.invoke()
                                        }
                                    )
                                }
                            }
                            else -> Unit
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
    }
}

data class SeriesPagingPreviewData(
    val loadState: CombinedLoadStates = stateData(),
    val itemCount: Int = 0,
    val items: List<TvShow> = listOf()
)

class SeriesPagingPreviewProvider: PreviewParameterProvider<SeriesPagingPreviewData> {

    override val values: Sequence<SeriesPagingPreviewData>
        get() = listOf(
            SeriesPagingPreviewData(
                loadState = stateFullLoading()
            ),
            SeriesPagingPreviewData(
                loadState = stateFullError()
            ),
            SeriesPagingPreviewData(
                loadState = stateData(),
                itemCount = 0
            ),
            SeriesPagingPreviewData(
                loadState = stateData(),
                itemCount = 5,
                items = listOf(
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
                    ),
                    TvShow(
                        false,
                        6,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 6"
                    )
                )
            ),
            SeriesPagingPreviewData(
                loadState = stateSingleLoading(),
                itemCount = 3,
                items = listOf(
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
                    )
                )
            ),
            SeriesPagingPreviewData(
                loadState = stateSingleError(),
                itemCount = 3,
                items = listOf(
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
                    )
                )
            ),
        ).asSequence()

}

@PreviewLightDark
@Composable
fun TvShowsPagingPreview(
    @PreviewParameter(SeriesPagingPreviewProvider::class) data: SeriesPagingPreviewData
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsPagingGrid(
                routeNavigation = { _, _ ->},
                item = { index ->
                    data.items[index]
                },
                loadStates = data.loadState,
                itemCount = data.itemCount,
                emptyState = {
                    TvShowsEmptyState(
                        title = "None movie was found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                },
                onRetry = {}
            )
        }
    }
}
