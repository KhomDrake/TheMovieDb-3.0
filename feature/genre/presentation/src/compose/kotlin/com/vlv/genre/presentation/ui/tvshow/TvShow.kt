package com.vlv.genre.presentation.ui.tvshow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.paging.series.SERIES_CONTENT_TYPE
import com.vlv.common.ui.paging.series.TvShowsPagingGrid
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowsGenreSuccess(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    genres: List<GenreResponse>
) {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { genres.size })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding()
            )
    ) {
        TabRow(
            itemCount = genres.size,
            currentIndex = pagerState.currentPage,
            onClick = { newIndex ->
                scope.launch {
                    pagerState.scrollToPage(newIndex)
                }
            }
        ) { index, isSelected ->
            val item = genres[index]
            TabItem(name = item.name, isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            val item = genres[it]
            TvShowsByGenre(
                genre = item,
                routeNavigation = routeNavigation
            )
        }
    }
}

@Composable
fun TvShowsByGenre(
    genre: GenreResponse,
    routeNavigation: RouteNavigation,
    seriesGenreViewModel: TvShowsGenreViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = genre.id, block = {
        seriesGenreViewModel.moviesByGenre(genre.id)
    })

    val tvShows = seriesGenreViewModel.flow.collectAsLazyPagingItems()

    TvShowsPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        loadStates = tvShows.loadState,
        itemCount = tvShows.itemCount,
        itemKey = tvShows.itemKey { item -> item.id },
        itemContentType = tvShows.itemContentType { item -> SERIES_CONTENT_TYPE },
        item = { index -> tvShows[index] },
        onRetry = {
            tvShows.retry()
        }
    )
}
