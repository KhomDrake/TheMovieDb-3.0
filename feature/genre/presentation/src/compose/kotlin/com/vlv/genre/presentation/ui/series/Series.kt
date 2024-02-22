package com.vlv.genre.presentation.ui.series

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
import com.vlv.common.ui.paging.SeriesPagingGrid
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesGenreSuccess(
    paddingValues: PaddingValues,
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
            SeriesByGenre(genre = item)
        }
    }
}

@Composable
fun SeriesByGenre(
    genre: GenreResponse,
    seriesGenreViewModel: SeriesGenreViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = genre.id, block = {
        seriesGenreViewModel.moviesByGenre(genre.id)
    })

    val series = seriesGenreViewModel.flow.collectAsLazyPagingItems()

    SeriesPagingGrid(
        seriesItems = series,
        routeNavigation = { _, _ -> },
        modifier = Modifier
            .fillMaxSize()
    )
}
