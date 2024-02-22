package com.vlv.common.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun GridPersonShimmer(
    modifier: Modifier = Modifier,
    count: Int = 6,
    columns: Int = 3,
    size: Dp = 64.dp
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        content = {
            items(count) {
                Column {
                    Box(
                        modifier = Modifier
                            .size(size)
                            .align(Alignment.CenterHorizontally)
                            .shimmer(shimmerInstance)
                            .background(
                                MaterialTheme.colorScheme.outline,
                                RoundedCornerShape(size / 2)
                            )
                    )
                }
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
}

@Composable
fun SinglePersonShimmer(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .align(Alignment.CenterHorizontally)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(size / 2)
                )
        )
    }
}

@PreviewLightDark
@Composable
fun PersonShimmerPrev() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            SinglePersonShimmer(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
fun PersonGridShimmerPrev() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            GridPersonShimmer(
                modifier = Modifier
                    .fillMaxWidth(),

            )
        }
    }
}