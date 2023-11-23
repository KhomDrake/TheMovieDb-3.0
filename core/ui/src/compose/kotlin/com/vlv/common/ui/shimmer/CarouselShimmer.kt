package com.vlv.common.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun CarouselShimmer(
    modifier: Modifier = Modifier,
    quantity: Int = 2
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        items(quantity) { index ->
            Box(
                modifier = Modifier
                    .fillParentMaxWidth(.8f)
                    .shimmer(shimmerInstance)
                    .height(150.dp)
                    .padding(
                        start = if (index == 0) 16.dp else 8.dp,
                        end = if (index == quantity - 1) 16.dp else 8.dp,
                    )
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}