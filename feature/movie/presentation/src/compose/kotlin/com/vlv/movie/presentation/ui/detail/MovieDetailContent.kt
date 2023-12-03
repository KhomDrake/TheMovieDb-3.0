package com.vlv.movie.presentation.ui.detail

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
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailContent(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    paddingValues: PaddingValues,
    movieDetailViewModel: MovieDetailViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val tabs = listOf(
        "About",
        "Cast",
        "Review",
        "Recommendation"
    )
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    LaunchedEffect(key1 = detailObject.id, block = {
        movieDetailViewModel.movieDetail(detailObject.id)
    })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding()
            )
    ) {
        TabRow(
            itemCount = tabs.size,
            currentIndex = pagerState.currentPage,
            onClick = {
                scope.launch {
                    pagerState.scrollToPage(it)
                }
            }
        ) { index, isSelected ->
            val item = tabs[index]
            TabItem(name = item, isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            
        }
    }

}