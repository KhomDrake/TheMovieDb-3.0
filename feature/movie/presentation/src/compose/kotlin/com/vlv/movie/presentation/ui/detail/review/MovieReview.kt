package com.vlv.movie.presentation.ui.detail.review

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
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.movie.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieReview(
    detailObject: DetailObject,
    viewModel: MovieReviewViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = detailObject.id, block = {
        viewModel.movieReviews(detailObject.id)
    })

    state.handle(
        success = {
            if(it.isEmpty()) {
                StateView(
                    icon = com.vlv.imperiya.core.R.drawable.ic_movie_enable,
                    title = stringResource(id = R.string.movie_empty_state_review),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        ),
                    iconTint = MaterialTheme.colorScheme.onBackground
                )
            } else {
                ReviewList(
                    reviews = it,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        },
        error = {
            SmallWarningView(
                title = "Erro",
                body = it?.stackTraceToString(),
                linkActionText = "Try again",
                showIconInfo = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            )
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