package com.vlv.genre.presentation.ui.movie

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
import com.vlv.common.ui.paging.movie.MOVIE_CONTENT_TYPE
import com.vlv.common.ui.paging.movie.MoviesPagingGrid
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGenreSuccess(
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
            MoviesByGenre(
                genre = item,
                routeNavigation = routeNavigation
            )
        }
    }
}

@Composable
fun MoviesByGenre(
    genre: GenreResponse,
    routeNavigation: RouteNavigation,
    movieGenreViewModel: MovieGenreViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = genre.id, block = {
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
        loadStates = movies.loadState
    )
}
