package com.vlv.series.presentation.ui.detail.recommendations

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.paging.SeriesPagingGrid
import com.vlv.imperiya.core.ui.components.StateView
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
        seriesItems = series,
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        emptyState = {
            StateView(
                icon = com.vlv.imperiya.core.R.drawable.ic_tv,
                title = stringResource(id = R.string.series_empty_state_recommendation),
                iconTint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    )
}