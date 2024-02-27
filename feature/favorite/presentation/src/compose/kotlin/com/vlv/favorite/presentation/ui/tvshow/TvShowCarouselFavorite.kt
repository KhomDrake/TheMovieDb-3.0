package com.vlv.favorite.presentation.ui.tvshow

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.lifecycle.Lifecycle
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import com.vlv.common.data.series.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.carousel.TvShowCarousel
import com.vlv.common.ui.extension.LaunchEffectLifecycle
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.paging.series.TvShowsErrorState
import com.vlv.common.ui.shimmer.CarouselShimmer
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowCarouselFavorite(
    errorTitle: String,
    errorBody: String,
    errorLink: String,
    emptyStateTitle: String,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: TvShowFavoriteViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchEffectLifecycle(
        event = Lifecycle.Event.ON_START,
        onEvent = {
            viewModel.seriesFavorite()
        }
    )

    TvShowCarouselFavoriteContent(
        errorTitle = errorTitle,
        errorBody = errorBody,
        errorLink = errorLink,
        emptyStateTitle = emptyStateTitle,
        routeNavigation = routeNavigation,
        onTryAgain = {
            viewModel.seriesFavorite()
        },
        modifier = modifier,
        state = state
    )
}

@Composable
fun TvShowCarouselFavoriteContent(
    state: Response<List<TvShow>>,
    routeNavigation: RouteNavigation,
    emptyStateTitle: String,
    errorTitle: String,
    errorBody: String,
    errorLink: String,
    onTryAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    state.handle(
        success = { tvShows ->
            TvShowCarousel(
                tvShows = tvShows,
                onClickSeries = routeNavigation,
                emptyStateTitle = emptyStateTitle
            )
        },
        loading = {
            CarouselShimmer(modifier)
        },
        error = {
            TvShowsErrorState(
                title = errorTitle,
                body = errorBody,
                linkActionText = errorLink,
                onTryAgain = onTryAgain,
                modifier = modifier
            )
        }
    )
}

class TvShowCarouselFavoriteContentProvider: PreviewParameterProvider<Response<List<TvShow>>> {

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
                )
            )
        ).asSequence()

}

@Composable
fun TvShowCarouselFavoriteContentPreview(
    @PreviewParameter(TvShowCarouselFavoriteContentProvider::class) data: Response<List<TvShow>>
) {
    
}
