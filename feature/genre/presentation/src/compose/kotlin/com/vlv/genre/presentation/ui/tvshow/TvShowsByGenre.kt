package com.vlv.genre.presentation.ui.tvshow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.LaunchEffectLifecycle
import com.vlv.common.ui.paging.series.TV_SHOW_CONTENT_TYPE
import com.vlv.common.ui.paging.series.TvShowsPagingGrid
import com.vlv.genre.presentation.data.Genre
import org.koin.androidx.compose.getViewModel

@Composable
fun TvShowsByGenre(
    genre: Genre,
    routeNavigation: RouteNavigation,
    tvShowsByGenreViewModel: TvShowsByGenreViewModel = getViewModel(
        key = genre.id.toString()
    )
) {
    LaunchEffectLifecycle(event = Lifecycle.Event.ON_START, onEvent = {
        tvShowsByGenreViewModel.tvShowsByGenre(genre.id)
    })

    val tvShows = tvShowsByGenreViewModel.tvShowByGenreState.collectAsLazyPagingItems()

    TvShowsPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        loadStates = tvShows.loadState,
        itemCount = tvShows.itemCount,
        itemKey = tvShows.itemKey { item -> item.id },
        itemContentType = tvShows.itemContentType { TV_SHOW_CONTENT_TYPE },
        item = { index -> tvShows[index] },
        onRetry = {
            tvShows.retry()
        }
    )
}
