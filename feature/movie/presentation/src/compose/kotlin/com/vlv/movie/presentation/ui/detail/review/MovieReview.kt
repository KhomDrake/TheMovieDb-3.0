package com.vlv.movie.presentation.ui.detail.review

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieReview(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: MovieReviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.movieReviews(detailObject.id)
    })

    MovieReviewContent(
        state = state,
        routeNavigation = routeNavigation,
        onTryAgain = {
            viewModel.movieReviews(detailObject.id)
        }
    )
}