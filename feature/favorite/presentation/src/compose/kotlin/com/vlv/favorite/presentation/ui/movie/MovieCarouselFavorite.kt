package com.vlv.favorite.presentation.ui.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.common.ui.MovieCarousel
import com.vlv.common.ui.shimmer.CarouselShimmer
import com.vlv.imperiya.core.ui.components.SmallWarningView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieCarouselFavorite(
    modifier: Modifier = Modifier,
    viewModel: MovieFavoriteViewModel = koinViewModel(),
    onClickMovie: RouteNavigation,
    errorTitle: String,
    errorBody: String,
    errorLink: String,
    emptyStateTitle: String
) {
    val data by viewModel.state.collectAsState()

    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val movies = data.data ?: return
            MovieCarousel(
                modifier,
                movies = movies,
                onClickMovie = {
                    onClickMovie.invoke(ScreenRoute.MOVIE_DETAIL, it)
                },
                emptyStateTitle = emptyStateTitle
            )
        }
        ResponseStatus.LOADING -> {
            CarouselShimmer(modifier)
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier,
                title = errorTitle,
                body = errorBody,
                linkActionText = errorLink,
                onClickLink = {
                    viewModel.moviesFavorite()
                }
            )
        }
        else -> Unit
    }
}