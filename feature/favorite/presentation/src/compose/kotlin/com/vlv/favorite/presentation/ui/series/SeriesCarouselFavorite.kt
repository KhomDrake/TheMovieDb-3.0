package com.vlv.favorite.presentation.ui.series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.common.ui.SeriesCarousel
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.shimmer.CarouselShimmer
import com.vlv.imperiya.core.ui.components.SmallWarningView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesCarouselFavorite(
    errorTitle: String,
    errorBody: String,
    errorLink: String,
    emptyStateTitle: String,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    viewModel: SeriesFavoriteViewModel = koinViewModel()
) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = errorTitle, block = {
        viewModel.seriesFavorite()
    })

    state.handle(
        success = { series ->
            SeriesCarousel(
                series = series,
                onClickSeries = {
                    routeNavigation.invoke(ScreenRoute.SERIES_DETAIL, it)
                },
                emptyStateTitle = emptyStateTitle
            )
        },
        loading = {
            CarouselShimmer(modifier)
        },
        error = {
            SmallWarningView(
                modifier,
                title = errorTitle,
                body = errorBody,
                linkActionText = errorLink,
                onClickLink = {
                    viewModel.seriesFavorite()
                }
            )
        }
    )

}