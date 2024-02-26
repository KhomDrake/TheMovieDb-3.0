package com.vlv.tv_show.presentation.ui.detail.about

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutTvShowTab(
    detailObject: DetailObject,
    viewModel: AboutTvShowViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seriesDetail(detailObject.id)
    })

    AboutTvShowsContent(
        state = state,
        onTryAgain = {
            viewModel.seriesDetail(detailObject.id)
        }
    )
}