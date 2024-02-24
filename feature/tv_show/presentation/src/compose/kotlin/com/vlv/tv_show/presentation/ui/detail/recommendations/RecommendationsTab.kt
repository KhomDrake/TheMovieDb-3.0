package com.vlv.tv_show.presentation.ui.detail.recommendations

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.paging.series.TV_SHOW_CONTENT_TYPE
import com.vlv.common.ui.paging.series.SeriesEmptyState
import com.vlv.common.ui.paging.series.TvShowsPagingGrid
import com.vlv.tv_show.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowRecommendationTab(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: TvShowsRecommendationViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.tvShowRecommendation(detailObject.id)
    })

    val series = viewModel.state.collectAsLazyPagingItems()

    TvShowsPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        loadStates = series.loadState,
        itemCount = series.itemCount,
        itemKey = series.itemKey { item -> item.id },
        itemContentType = series.itemContentType { item -> TV_SHOW_CONTENT_TYPE },
        item = { index -> series[index] },
        onRetry = {
            series.retry()
        },
        emptyState = {
            SeriesEmptyState(
                title = stringResource(id = R.string.tv_show_empty_state_recommendation),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    )
}