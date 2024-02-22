package com.vlv.themoviedb.ui.series

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.series.Series
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.common.ui.carousel.SeriesCarousel
import com.vlv.common.ui.shimmer.CarouselShimmer
import com.vlv.favorite.presentation.ui.series.SeriesCarouselFavorite
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.SeeAll
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesScreen(
    paddingValues: PaddingValues,
    onNavigate: RouteNavigation
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = paddingValues.calculateBottomPadding()
            )
            .verticalScroll(scrollState)
    ) {
        SearchComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Search for series",
            onClick = {
                onNavigate.invoke(ScreenRoute.SERIES_SEARCH, null)
            },
            enable = false
        )
        SeriesTrending(onNavigate = onNavigate)
        AiringToday(onNavigate = onNavigate)
        SeeAll(
            title = stringResource(id = R.string.favorites_title),
            onClickSeeAll = {
                onNavigate.invoke(ScreenRoute.FAVORITES_SERIES, null)
            }
        )
        SeriesCarouselFavorite(
            modifier = Modifier
                .padding(16.dp),
            routeNavigation = onNavigate,
            errorTitle = stringResource(id = R.string.error_series_load_text_title_favorites),
            errorBody = stringResource(id = com.vlv.ui.R.string.common_error_description),
            errorLink = stringResource(id = com.vlv.ui.R.string.common_error_button),
            emptyStateTitle = stringResource(id = R.string.empty_state_text_series_favorite)
        )
    }
}

@Composable
fun AiringToday(
    viewModel: AiringTodayViewModel = koinViewModel(),
    onNavigate: RouteNavigation
) {
    val airingToday by viewModel.state.collectAsState()

    SeriesInformation(
        title = stringResource(id = R.string.airing_today_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_series_airing_today),
        data = airingToday,
        onNavigate = onNavigate,
        titleWarning = stringResource(id = R.string.error_series_load_text_title_airing_today),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button),
        onError = {
            viewModel.airingToday()
        },
        onSeeAll = {
            onNavigate.invoke(ScreenRoute.SERIES_AIRING_TODAY, null)
        }
    )

}

@Composable
fun SeriesTrending(
    viewModel: SeriesTrendingViewModel = koinViewModel(),
    onNavigate: RouteNavigation
) {
    val trending by viewModel.state.collectAsState()

    SeriesInformation(
        title = stringResource(id = R.string.trending_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_series_trending),
        data = trending,
        onNavigate = onNavigate,
        titleWarning = stringResource(id = R.string.error_series_load_text_title_trending),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button),
        onError = {
            viewModel.trending()
        },
        onSeeAll = {
            onNavigate.invoke(ScreenRoute.SERIES_TRENDING, null)
        },
        percentage = 1f
    )
}

@Composable
fun SeriesInformation(
    title: String,
    emptyStateTitle: String,
    titleWarning: String,
    bodyWarning: String,
    linkTextWarning: String,
    data: Response<List<Series>>,
    onNavigate: RouteNavigation,
    percentage: Float = .8f,
    onSeeAll: () -> Unit,
    onError: () -> Unit
) {
    SeeAll(title = title) {
        onSeeAll.invoke()
    }

    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val series = data.data ?: return
            SeriesCarousel(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                series = series,
                emptyStateTitle = emptyStateTitle,
                onClickSeries = {
                    onNavigate.invoke(ScreenRoute.SERIES_DETAIL, it)
                },
                percentage = percentage
            )
        }
        ResponseStatus.LOADING -> {
            CarouselShimmer()
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                title = titleWarning,
                body = bodyWarning,
                linkActionText = linkTextWarning,
                onClickLink = onError
            )
        }
        else -> Unit
    }
}
