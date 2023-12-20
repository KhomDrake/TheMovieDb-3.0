package com.vlv.series.presentation.ui.detail.reviews

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.data.review.Review
import com.vlv.common.ui.review.ReviewList
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.StateView

@Composable
fun ReviewSuccess(reviews: List<Review>) {
    if(reviews.isEmpty()) {
        StateView(
            icon = R.drawable.ic_tv,
            title = stringResource(id = com.vlv.ui.R.string.common_review_empty_state_title),
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
            reviews = reviews,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}