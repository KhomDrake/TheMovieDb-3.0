package com.vlv.series.presentation.ui.detail.recommendations

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
import com.vlv.common.ui.paging.series.SERIES_CONTENT_TYPE
import com.vlv.common.ui.paging.series.SeriesEmptyState
import com.vlv.common.ui.paging.series.SeriesPagingGrid
import com.vlv.series.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesRecommendationTab(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: SeriesRecommendationViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seriesRecommendation(detailObject.id)
    })

    val series = viewModel.state.collectAsLazyPagingItems()

    SeriesPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        loadStates = series.loadState,
        itemCount = series.itemCount,
        itemKey = series.itemKey { item -> item.id },
        itemContentType = series.itemContentType { item -> SERIES_CONTENT_TYPE },
        item = { index -> series[index] },
        onRetry = {
            series.retry()
        },
        emptyState = {
            SeriesEmptyState(
                title = stringResource(id = R.string.series_empty_state_recommendation),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    )
}