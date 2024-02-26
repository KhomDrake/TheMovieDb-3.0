package com.vlv.movie.presentation.ui.detail.cast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieCast(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    viewModel: MovieCastViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.movieCast(detailObject.id)
    })

    MovieCastContent(
        state = state,
        routeNavigation = routeNavigation,
        tryAgain = {
            viewModel.movieCast(detailObject.id)
        }
    )
}
