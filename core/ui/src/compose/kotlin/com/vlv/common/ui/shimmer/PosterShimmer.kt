package com.vlv.common.ui.shimmer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun GridPosterShimmer(
    modifier: Modifier = Modifier,
    count: Int = 2,
    columns: Int = 2,
    height: Dp = 150.dp
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        content = {
            items(count) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .shimmer(shimmerInstance)
                        .background(
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(16.dp)
                        )
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
}

@Composable
fun ColumnPosterShimmer(
    modifier: Modifier = Modifier,
    count: Int = 2,
    height: Dp = 150.dp
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    LazyColumn(
        modifier = modifier,
        content = {
            items(count) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height)
                        .shimmer(shimmerInstance)
                        .background(
                            MaterialTheme.colorScheme.outline,
                            RoundedCornerShape(16.dp)
                        )
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    )
}

@Composable
fun SinglePosterShimmer(
    modifier: Modifier = Modifier,
    height: Dp = 150.dp
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Box(
        modifier = modifier
            .height(height)
            .shimmer(shimmerInstance)
            .background(
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(16.dp)
            )
    )
}

@PreviewLightDark
@Composable
private fun SinglePosterShimmerPrev() {
    TheMovieDbAppTheme {
        Column {
            SinglePosterShimmer(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun GridPosterShimmerPrev() {
    TheMovieDbAppTheme {
        Column {
            GridPosterShimmer(
                modifier = Modifier
                    .fillMaxWidth(),
                height = 200.dp,
                count = 4
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun ColumnPosterShimmerPrev() {
    TheMovieDbAppTheme {
        Column {
            ColumnPosterShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp, 16.dp),
                height = 200.dp,
                count = 4
            )
        }
    }
}
