package com.vlv.movie.presentation.ui.detail.recommendation

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
import com.vlv.common.ui.paging.MoviesPagingGrid
import com.vlv.imperiya.core.ui.components.StateView
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
        movies = movies,
        routeNavigation = routeNavigation,
        modifier = Modifier
            .fillMaxSize(),
        emptyState = {
            StateView(
                icon = com.vlv.imperiya.core.R.drawable.ic_movie,
                title = stringResource(id = R.string.movie_empty_state_recommendation),
                iconTint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    )
}