package com.vlv.tv_show.presentation.ui.detail.seasons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun SeasonsShimmer(
    modifier: Modifier = Modifier
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(8.dp)
                )
        )
    }
}

@PreviewLightDark
@Composable
fun SeasonsShimmerPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SeasonsShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}