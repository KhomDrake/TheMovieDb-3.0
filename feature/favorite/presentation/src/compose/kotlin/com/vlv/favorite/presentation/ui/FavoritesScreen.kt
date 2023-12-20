package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.MoviePoster
import com.vlv.common.ui.SeriesPoster
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesScreen(
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier
) {
    val items = listOf(
        R.string.favorite_movie_title,
        R.string.favorite_people_title
    )
    val pagerState = rememberPagerState(pageCount = { items.size })
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        TabRow(
            itemCount = items.size,
            currentIndex = pagerState.currentPage,
            onClick = { newIndex ->
                scope.launch {
                    pagerState.scrollToPage(newIndex)
                }
            }
        ) { index, isSelected ->
            val item = stringResource(id = items[index])
            TabItem(name = item, isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            when(items[pagerState.currentPage]) {
                R.string.favorite_movie_title -> MovieFavorites(routeNavigation)
                else -> SeriesFavorites(routeNavigation)
            }
        }
    }

}

@Composable
fun MovieFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.movieState.collectAsState(listOf())

    LaunchedEffect(key1 = 2, block = {
        favoritesViewModel.moviesFavorites()
    })

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(state) { movie ->
                MoviePoster(
                    movie = movie,
                    height = 200.dp,
                    onRouteNavigation = routeNavigation
                )
            }
        }
    )
}

@Composable
fun SeriesFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.seriesState.collectAsState(listOf())

    LaunchedEffect(key1 = 2, block = {
        favoritesViewModel.seriesFavorites()
    })

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        content = {
            items(state) { series ->
                SeriesPoster(
                    series = series,
                    height = 200.dp,
                    onRouteNavigation = routeNavigation
                )
            }
        }
    )
}