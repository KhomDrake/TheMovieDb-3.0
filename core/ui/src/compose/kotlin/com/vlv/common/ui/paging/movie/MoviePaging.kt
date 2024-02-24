package com.vlv.common.ui.paging.movie

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
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.firstLoadingFinished
import com.vlv.common.ui.extension.isFullError
import com.vlv.common.ui.extension.isFullLoading
import com.vlv.common.ui.extension.isSingleError
import com.vlv.common.ui.extension.isSingleLoading
import com.vlv.common.ui.extension.stateData
import com.vlv.common.ui.extension.stateFullError
import com.vlv.common.ui.extension.stateFullLoading
import com.vlv.common.ui.extension.stateSingleError
import com.vlv.common.ui.extension.stateSingleLoading
import com.vlv.common.ui.poster.MoviePoster
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePosterShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

const val MOVIE_CONTENT_TYPE = "MOVIE_CONTENT_TYPE"

@Composable
fun MoviesPagingGrid(
    routeNavigation: RouteNavigation,
    item: (Int) -> Movie?,
    loadStates: CombinedLoadStates,
    itemCount: Int,
    modifier: Modifier = Modifier,
    itemKey: ((index: Int) -> Any)? = null,
    itemContentType: (index: Int) -> Any? = { null },
    itemCountInitialLoading: Int = 4,
    heightItem: Dp = 200.dp,
    columns: Int = 2,
    emptyState: @Composable () -> Unit = {
         MovieEmptyState(
             title = stringResource(
                 id = R.string.common_movie_empty_view_title_default
             ),
             modifier = Modifier
                 .fillMaxWidth()
                 .padding(16.dp),
         )
    },
    onRetry: () -> Unit = {}
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
            MovieErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onTryAgain = {
                    onRetry.invoke()
                }
            )
        }
        loadStates.firstLoadingFinished() -> {
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
                            item.invoke(index)?.let { movie ->
                                MoviePoster(
                                    movie = movie,
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
                                    MovieErrorState(
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
        else -> Unit
    }
}

data class MoviePagingPreviewData(
    val loadState: CombinedLoadStates = stateData(),
    val itemCount: Int = 0,
    val items: List<Movie> = listOf()
)

class MoviePagingPreviewProvider: PreviewParameterProvider<MoviePagingPreviewData> {

    override val values: Sequence<MoviePagingPreviewData>
        get() = listOf(
            MoviePagingPreviewData(
                loadState = stateFullLoading()
            ),
            MoviePagingPreviewData(
                loadState = stateFullError()
            ),
            MoviePagingPreviewData(
                loadState = stateData(),
                itemCount = 0
            ),
            MoviePagingPreviewData(
                loadState = stateData(),
                itemCount = 5,
                items = listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        3,
                        "Duna 2",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        4,
                        "Duna 3",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        5,
                        "Duna 4",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        6,
                        "Duna 5",
                        "asda"
                    )
                )
            ),
            MoviePagingPreviewData(
                loadState = stateSingleLoading(),
                itemCount = 3,
                items = listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        3,
                        "Duna 2",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        4,
                        "Duna 3",
                        "asda"
                    )
                )
            ),
            MoviePagingPreviewData(
                loadState = stateSingleError(),
                itemCount = 3,
                items = listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        3,
                        "Duna 2",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        4,
                        "Duna 3",
                        "asda"
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MoviePagingPreview(
    @PreviewParameter(MoviePagingPreviewProvider::class) data: MoviePagingPreviewData
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MoviesPagingGrid(
                routeNavigation = { _, _ ->},
                item = { index ->
                     data.items[index]
                },
                loadStates = data.loadState,
                itemCount = data.itemCount,
                emptyState = {
                    MovieEmptyState(
                        title = "None movie was found",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
            )
        }
    }
}
