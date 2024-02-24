package com.vlv.genre.presentation.ui.movie

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.LaunchEffectLifecycle
import com.vlv.common.ui.paging.movie.MOVIE_CONTENT_TYPE
import com.vlv.common.ui.paging.movie.MoviesPagingGrid
import com.vlv.genre.presentation.data.Genre
import org.koin.androidx.compose.getViewModel

@Composable
fun MoviesByGenre(
    genre: Genre,
    routeNavigation: RouteNavigation,
    movieGenreViewModel: MoviesByGenreViewModel = getViewModel(
        key = genre.id.toString()
    )
) {
    LaunchEffectLifecycle(event = Lifecycle.Event.ON_START, onEvent = {
        movieGenreViewModel.moviesByGenre(genre.id)
    })

    val movies = movieGenreViewModel.movieState.collectAsLazyPagingItems()

    MoviesPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        itemCount = movies.itemCount,
        item = { index -> movies[index] },
        itemKey = movies.itemKey { movie -> movie.apiId },
        itemContentType = movies.itemContentType { MOVIE_CONTENT_TYPE },
        loadStates = movies.loadState,
        onRetry = {
            movies.retry()
        }
    )
}
