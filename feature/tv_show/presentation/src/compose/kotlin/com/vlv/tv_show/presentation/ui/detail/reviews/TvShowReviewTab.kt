package com.vlv.tv_show.presentation.ui.detail.reviews

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.vlv.common.ui.DetailObject
import org.koin.androidx.compose.koinViewModel

@Composable
fun TvShowReviewTab(
    detailObject: DetailObject,
    viewModel: TvShowReviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.tvShowReview(detailObject.id)
    })

    TvShowReviewContent(
        state = state,
        onTryAgain = {
            viewModel.tvShowReview(detailObject.id)
        }
    )
}