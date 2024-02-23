package com.vlv.tv_show.presentation.ui.detail

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
import com.vlv.tv_show.R
import com.vlv.tv_show.presentation.ui.detail.about.AboutTvShowTab
import com.vlv.tv_show.presentation.ui.detail.cast.TvShowCastTab
import com.vlv.tv_show.presentation.ui.detail.recommendations.TvShowRecommendationTab
import com.vlv.tv_show.presentation.ui.detail.reviews.TvShowReviewTab
import com.vlv.tv_show.presentation.ui.detail.seasons.SeasonsTab
import kotlinx.coroutines.launch

enum class SeriesDetailTab(
    @StringRes
    val title: Int
) {
    ABOUT(R.string.tv_show_about_tab_title),
    SEASONS(R.string.tv_show_seasons_tab_title),
    CAST(R.string.tv_show_cast_tab_title),
    REVIEWS(R.string.tv_show_review_tab_title),
    RECOMMENDATIONS(R.string.tv_show_recommendation_tab_title)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesDetailContent(
    detailObject: DetailObject,
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation
) {

    val scope = rememberCoroutineScope()
    val tabs = SeriesDetailTab.values()
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding())
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
                SeriesDetailTab.ABOUT -> {
                    AboutTvShowTab(paddingValues = PaddingValues(), detailObject = detailObject)
                }
                SeriesDetailTab.SEASONS -> {
                    SeasonsTab(
                        detailObject = detailObject,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
                SeriesDetailTab.CAST -> {
                    TvShowCastTab(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
                SeriesDetailTab.REVIEWS -> {
                    TvShowReviewTab(detailObject = detailObject)
                }
                else -> {
                    TvShowRecommendationTab(
                        detailObject = detailObject,
                        routeNavigation = routeNavigation
                    )
                }
            }
        }
    }
}