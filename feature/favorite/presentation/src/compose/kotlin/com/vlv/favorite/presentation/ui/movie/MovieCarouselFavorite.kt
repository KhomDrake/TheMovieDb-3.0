package com.vlv.favorite.presentation.ui.movie

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.carousel.MovieCarousel
import com.vlv.common.ui.extension.handle
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
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = errorTitle, block = {
        viewModel.moviesFavorites()
    })

    state.handle(
        success = { movies ->
            MovieCarousel(
                movies = movies,
                onClickMovie = onClickMovie,
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
                    viewModel.moviesFavorites()
                }
            )
        }
    )
}