package com.vlv.favorite.presentation.ui.tvshow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.LaunchEffectLifecycle
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.TvShowsGrid
import com.vlv.common.ui.paging.series.TvShowsErrorState
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: TvShowFavoriteViewModel = koinViewModel()
) {
    val state by favoritesViewModel.state.collectAsState()

    LaunchEffectLifecycle(
        event = Lifecycle.Event.ON_START,
        onEvent = {
            favoritesViewModel.seriesFavorite()
        }
    )

    TvShowFavoriteContent(
        state = state,
        routeNavigation = routeNavigation,
        onTryAgain = { favoritesViewModel.seriesFavorite() },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun TvShowFavoriteContent(
    state: Response<List<TvShow>>,
    routeNavigation: RouteNavigation,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    state.handle(
        success = { data ->
            TvShowsGrid(
                tvShows = data,
                routeNavigation = routeNavigation,
                emptyStateTitle = stringResource(id = R.string.favorite_tv_show_empty_state),
                modifier = modifier
            )
        },
        error = {
            TvShowsErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onTryAgain = onTryAgain
            )
        },
        loading = {
            GridPosterShimmer(
                modifier = modifier,
                count = 6
            )
        }
    )
}

class SeriesFavoriteContentPreviewProvider: PreviewParameterProvider<Response<List<TvShow>>> {

    override val values: Sequence<Response<List<TvShow>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
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
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun SeriesFavoriteContentPreview(
    @PreviewParameter(SeriesFavoriteContentPreviewProvider::class) data: Response<List<TvShow>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowFavoriteContent(
                state = data,
                routeNavigation = { _, _ ->},
                onTryAgain = { },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
