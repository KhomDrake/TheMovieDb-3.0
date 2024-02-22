package com.vlv.people.ui.detail.tab

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.SeriesList
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.StateView
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
            if(it.isEmpty()) {
                StateView(
                    icon = com.vlv.imperiya.core.R.drawable.ic_movie,
                    title = stringResource(id = R.string.people_credit_empty_title),
                    iconTint = MaterialTheme.colorScheme.onBackground,
                    modifier = modifier
                )
            } else {
                SeriesList(
                    series = it,
                    routeNavigation = routeNavigation,
                    modifier = modifier
                )
            }
        },
        loading = {
            GridPosterShimmer(
                count = 6,
                modifier = modifier
            )
        },
        error = {
            SmallWarningView(
                title = stringResource(id = com.vlv.ui.R.string.common_error_title),
                body = stringResource(id = com.vlv.ui.R.string.common_error_description),
                linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                onClickLink = {
                    onTryAgain.invoke()
                },
                modifier = modifier
            )
        }
    )
}

class SeriesCreditContentStatesProvider: PreviewParameterProvider<Response<List<Series>>> {

    override val values: Sequence<Response<List<Series>>>
        get() = listOf<Response<List<Series>>>(
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
                        2,
                        null,
                        null,
                        "Bleach",
                        2
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
