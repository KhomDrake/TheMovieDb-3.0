package com.vlv.movie.presentation.ui.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailContent(
    detailObject: DetailObject,
    routeNavigation: RouteNavigation,
    paddingValues: PaddingValues,
    movieDetailViewModel: MovieDetailViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = detailObject.id, block = {
        movieDetailViewModel.movieDetail(detailObject.id)
    })

}