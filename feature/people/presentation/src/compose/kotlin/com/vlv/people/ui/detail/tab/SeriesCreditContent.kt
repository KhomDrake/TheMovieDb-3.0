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
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.SeriesGrid
import com.vlv.common.ui.paging.series.SeriesErrorState
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.people.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesCreditContent(
    people: People,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: PeopleCreditViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people, block = {
        viewModel.seriesCredit(people)
    })

    val state by viewModel.seriesCreditState.collectAsState()

    SeriesCreditContentStates(
        state = state,
        routeNavigation = routeNavigation,
        modifier = modifier,
        onTryAgain = {
            viewModel.seriesCredit(people)
        }
    )
}

@Composable
fun SeriesCreditContentStates(
    state: Response<List<Series>>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit = {}
) {
    state.handle(
        success = {
            SeriesGrid(
                series = it,
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
            SeriesErrorState(
                title = stringResource(id = com.vlv.ui.R.string.common_error_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onTryAgain = onTryAgain
            )
        }
    )
}

class SeriesCreditContentStatesProvider: PreviewParameterProvider<Response<List<Series>>> {

    override val values: Sequence<Response<List<Series>>>
        get() = listOf(
            responseLoading(),
            responseError(null),
            responseData(listOf()),
            responseData(
                listOf(
                    Series(
                        false,
                        2,
                        null,
                        null,
                        "Naruto",
                        2
                    ),
                    Series(
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
fun SeriesCreditContentStatesPrev(
    @PreviewParameter(SeriesCreditContentStatesProvider::class) state: Response<List<Series>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeriesCreditContentStates(
                state = state,
                routeNavigation = { _, _ ->

                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}
