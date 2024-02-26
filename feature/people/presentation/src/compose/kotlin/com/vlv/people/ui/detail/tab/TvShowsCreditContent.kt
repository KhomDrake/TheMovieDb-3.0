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
import com.vlv.common.data.people.People
import com.vlv.common.data.series.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.TvShowsGrid
import com.vlv.common.ui.paging.series.TvShowsErrorState
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.people.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowsCreditContent(
    people: People,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: PeopleCreditViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people, block = {
        viewModel.seriesCredit(people)
    })

    val state by viewModel.tvShowsCreditState.collectAsState()

    TvShowsCreditContentStates(
        state = state,
        routeNavigation = routeNavigation,
        modifier = modifier,
        onTryAgain = {
            viewModel.seriesCredit(people)
        }
    )
}

@Composable
fun TvShowsCreditContentStates(
    state: Response<List<TvShow>>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit = {}
) {
    state.handle(
        success = {
            TvShowsGrid(
                tvShows = it,
                routeNavigation = routeNavigation,
                modifier = modifier,
                emptyStateTitle = stringResource(id = R.string.people_credit_empty_title),
            )
        },
        loading = {
            GridPosterShimmer(
                count = 6,
                modifier = modifier
            )
        },
        error = {
            TvShowsErrorState(
                title = stringResource(id = com.vlv.ui.R.string.common_error_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onTryAgain = onTryAgain
            )
        }
    )
}

class SeriesCreditContentStatesProvider: PreviewParameterProvider<Response<List<TvShow>>> {

    override val values: Sequence<Response<List<TvShow>>>
        get() = listOf(
            responseLoading(),
            responseError(null),
            responseData(listOf()),
            responseData(
                listOf(
                    TvShow(
                        false,
                        2,
                        null,
                        null,
                        "Naruto",
                        2
                    ),
                    TvShow(
                        false,
                        3,
                        null,
                        null,
                        "Bleach",
                        3
                    )
                )
            )
        ).asSequence()

}

@PreviewLightDark
@Composable
fun TvShowsCreditContentStatesPrev(
    @PreviewParameter(SeriesCreditContentStatesProvider::class) state: Response<List<TvShow>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsCreditContentStates(
                state = state,
                routeNavigation = { _, _ ->

                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
