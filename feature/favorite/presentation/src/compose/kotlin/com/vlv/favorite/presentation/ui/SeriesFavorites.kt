package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.grid.SeriesGrid
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.seriesState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        favoritesViewModel.seriesFavorites()
    })

    SeriesFavoriteContent(
        state = state,
        routeNavigation = routeNavigation,
        onTryAgain = { favoritesViewModel.seriesFavorites() },
        modifier = Modifier
            .fillMaxSize()
    )
}

@Composable
fun SeriesFavoriteContent(
    state: Response<List<Series>>,
    routeNavigation: RouteNavigation,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        state.handle(
            success = { data ->
                if(data.isEmpty()) {
                    StateView(
                        icon = com.vlv.imperiya.core.R.drawable.ic_tv,
                        iconTint = MaterialTheme.colorScheme.onBackground,
                        title = stringResource(id = R.string.favorite_series_empty_state),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    SeriesGrid(
                        series = data,
                        routeNavigation = routeNavigation
                    )
                }
            },
            error = {
                SmallWarningView(
                    title = stringResource(id = R.string.favorite_error_state_title),
                    body = stringResource(id = com.vlv.ui.R.string.common_error_description),
                    linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                    onClickLink = onTryAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                )
            },
            loading = {
                GridPosterShimmer(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        )
    }
}

class SeriesFavoriteContentPreviewProvider: PreviewParameterProvider<Response<List<Series>>> {

    override val values: Sequence<Response<List<Series>>>
        get() = listOf(
            responseLoading(),
            responseError(Throwable()),
            responseData(listOf()),
            responseData(
                listOf(
                    Series(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    Series(
                        false,
                        3,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 2"
                    ),
                    Series(
                        false,
                        4,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 3"
                    ),
                    Series(
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
    @PreviewParameter(SeriesFavoriteContentPreviewProvider::class) data: Response<List<Series>>
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeriesFavoriteContent(
                state = data,
                routeNavigation = { _, _ ->},
                onTryAgain = { },
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
