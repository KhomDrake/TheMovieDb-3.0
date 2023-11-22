package com.vlv.genre.presentation.ui.movie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.extensions.fullyLoaded
import com.vlv.extensions.notLoading
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieGenreSuccess(
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
            contentPadding = PaddingValues(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp,
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            val item = genres[pagerState.currentPage]
            MoviesByGenre(genre = item)
        }
    }
}

@Composable
fun MoviesByGenre(
    genre: GenreResponse,
    movieGenreViewModel: MovieGenreViewModel = koinViewModel()
) {

    LaunchedEffect(key1 = {}, block = {
        movieGenreViewModel.moviesByGenre(genre.id)
    })

    val movies = movieGenreViewModel.flow.collectAsLazyPagingItems()

//    when(movies.loadState.refresh) {
//        LoadState.Loading -> {
//            Text(
//                text = "Loading",
//                style = TheMovieDbTypography.TitleStyle,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//        is LoadState.Error -> {
//            Text(
//                text = "Error",
//                style = TheMovieDbTypography.TitleStyle,
//                color = MaterialTheme.colorScheme.onBackground
//            )
//        }
//        else -> {
//            Text(
//                text = movies.itemCount.toString(),
//                style = TheMovieDbTypography.TitleStyle,
//                color = MaterialTheme.colorScheme.onBackground
//            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                content = {
                    itemsIndexed(movies) { index: Int, value: MovieResponse? ->
                        value?.let {
                            Text(
                                text = it.title,
                                style = TheMovieDbTypography.TitleStyle,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            )
//        }
//    }
}
