package com.vlv.favorite.presentation.ui.movie

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
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.LaunchEffectLifecycle
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.MovieGrid
import com.vlv.common.ui.paging.movie.MovieErrorState
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: MovieFavoriteViewModel = koinViewModel()
) {
    val state by favoritesViewModel.state.collectAsState()

    LaunchEffectLifecycle(
        event = Lifecycle.Event.ON_START,
        onEvent = {
            favoritesViewModel.moviesFavorites()
        }
    )

    MovieFavoritesContent(
        state = state,
        routeNavigation = routeNavigation,
        onTryAgain = {
            favoritesViewModel.moviesFavorites()
        },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun MovieFavoritesContent(
    state: Response<List<Movie>>,
    routeNavigation: RouteNavigation,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier,
    columns: Int = 2
) {
    state.handle(
        success = { data ->
            MovieGrid(
                movies = data,
                routeNavigation = routeNavigation,
                emptyStateTitle = stringResource(id = R.string.favorite_movie_empty_state),
                modifier = modifier
            )
        },
        error = {
            MovieErrorState(
                title = stringResource(id = R.string.favorite_error_state_title),
                onTryAgain = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        loading = {
            GridPosterShimmer(
                modifier,
                columns = columns,
                count = 4
            )
        }
    )
}

class MovieFavoritesContentPreviewProvider: PreviewParameterProvider<Response<List<Movie>>> {

    override val values: Sequence<Response<List<Movie>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
                listOf(
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
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MovieFavoritesContentPreview(
    @PreviewParameter(MovieFavoritesContentPreviewProvider::class) data: Response<List<Movie>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieFavoritesContent(
                state = data,
                routeNavigation = { _, _ ->

                },
                onTryAgain = {

                },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
