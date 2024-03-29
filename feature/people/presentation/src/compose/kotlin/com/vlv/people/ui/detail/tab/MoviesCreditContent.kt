package com.vlv.people.ui.detail.tab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.people.People
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.MovieGrid
import com.vlv.common.ui.paging.movie.MovieErrorState
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.people.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MoviesCreditContent(
    people: People,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: PeopleCreditViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people, block = {
        viewModel.moviesCredit(people)
    })
    
    val state by viewModel.movieCreditState.collectAsState()
    
    MoviesCreditContentStates(
        state = state,
        routeNavigation = routeNavigation,
        modifier = modifier,
        onTryAgain = {
            viewModel.moviesCredit(people)
        }
    )
}

@Composable
fun MoviesCreditContentStates(
    state: Response<List<Movie>>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit = {}
) {
    state.handle(
        success = {
            MovieGrid(
                movies = it,
                routeNavigation = routeNavigation,
                modifier = modifier,
                emptyStateTitle = stringResource(id = R.string.people_credit_empty_title)
            )
        },
        loading = {
            GridPosterShimmer(
                count = 6,
                modifier = modifier
            )
        },
        error = {
            MovieErrorState(
                onTryAgain = onTryAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    )
}

class MoviesCreditContentStatesProvider: PreviewParameterProvider<Response<List<Movie>>> {

    override val values: Sequence<Response<List<Movie>>>
        get() = listOf(
            responseLoading(),
            responseError(throwable = null),
            responseData(
                listOf()
            ),
            responseData(
                listOf(
                    Movie(
                        false,
                        null,
                        null,
                        2,
                        "Avengers 1",
                        "alsdkjalkjdsal"
                    ),
                    Movie(
                        false,
                        null,
                        null,
                        3,
                        "Avengers 2",
                        "alsdkjalkjdsal"
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MoviesCreditContentStatesPrev(
    @PreviewParameter(MoviesCreditContentStatesProvider::class) state: Response<List<Movie>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MoviesCreditContentStates(
                state = state,
                modifier = Modifier
                    .fillMaxWidth(),
                routeNavigation = { _, _ ->

                }
            )
        }
    }
}