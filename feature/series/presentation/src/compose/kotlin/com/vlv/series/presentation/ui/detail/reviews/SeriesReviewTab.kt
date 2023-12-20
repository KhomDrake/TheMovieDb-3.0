package com.vlv.series.presentation.ui.detail.reviews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.review.ReviewList
import com.vlv.common.ui.review.ReviewShimmer
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.StateView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesReviewTab(
    detailObject: DetailObject,
    viewModel: SeriesReviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.seriesReview(detailObject.id)
    })

    state.handle(
        success = {
            ReviewSuccess(reviews = it)
        },
        error = {

        },
        loading = {
            ReviewShimmer(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            )
        }
    )
}