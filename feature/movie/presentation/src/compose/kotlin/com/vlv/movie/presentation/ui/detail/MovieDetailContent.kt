package com.vlv.movie.presentation.ui.detail

import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.movie.R
import com.vlv.movie.presentation.ui.detail.about.MovieAbout
import com.vlv.movie.presentation.ui.detail.cast.MovieCast
import com.vlv.movie.presentation.ui.detail.recommendation.MovieRecommendation
import com.vlv.movie.presentation.ui.detail.review.MovieReview
import kotlinx.coroutines.launch

enum class MovieDetailPage(
    @StringRes
    val title: Int
) {
    ABOUT(R.string.movie_about_tab_title),
    CAST(R.string.movie_cast_tab_title),
    REVIEW(R.string.movie_review_tab_title),
    RECOMMENDATION(R.string.movie_recommendation_tab_title)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieDetailContent(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    paddingValues: PaddingValues
) {
    val scope = rememberCoroutineScope()
    val tabs = MovieDetailPage.values()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(
        modifier = Modifier
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
            TabItem(name = stringResource(id = item.title), isSelected = isSelected)
        }

        HorizontalPager(
            state = pagerState,
            pageSpacing = 16.dp,
            modifier = Modifier
                .fillMaxSize()
        ) { index ->
            when(tabs[index]) {
                MovieDetailPage.ABOUT -> {
                    MovieAbout(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
                MovieDetailPage.CAST -> {
                    MovieCast(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
                MovieDetailPage.RECOMMENDATION -> {
                    MovieRecommendation(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
                MovieDetailPage.REVIEW -> {
                    MovieReview(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
            }
        }
    }

}