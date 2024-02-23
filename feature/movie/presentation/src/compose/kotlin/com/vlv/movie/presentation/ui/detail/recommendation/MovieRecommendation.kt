package com.vlv.movie.presentation.ui.detail.recommendation

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
import com.vlv.common.ui.paging.movie.MOVIE_CONTENT_TYPE
import com.vlv.common.ui.paging.movie.MovieEmptyState
import com.vlv.common.ui.paging.movie.MoviesPagingGrid
import com.vlv.movie.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieRecommendation(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: MovieRecommendationViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.movieRecommendation(detailObject.id)
    })

    val movies = viewModel.state.collectAsLazyPagingItems()

    MoviesPagingGrid(
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        emptyState = {
            MovieEmptyState(
                title = stringResource(id = R.string.movie_empty_state_recommendation),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        },
        itemCount = movies.itemCount,
        item = { index -> movies[index] },
        itemKey = movies.itemKey { movie -> movie.apiId },
        itemContentType = movies.itemContentType { MOVIE_CONTENT_TYPE },
        loadStates = movies.loadState
    )
}