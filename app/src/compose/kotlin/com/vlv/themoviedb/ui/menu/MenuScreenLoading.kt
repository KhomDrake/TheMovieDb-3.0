package com.vlv.themoviedb.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun MenuScreenLoading(
    modifier: Modifier = Modifier
) {
    val shimmer = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(48.dp)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .shimmer(shimmer)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        end = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        horizontal = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        start = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        end = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        horizontal = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        start = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        end = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        horizontal = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        start = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(48.dp)
                .padding(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp
                )
                .shimmer(shimmer)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 12.dp,
                    start = 16.dp,
                    end = 16.dp
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        end = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        horizontal = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
            Box(
                modifier = Modifier
                    .weight(3f)
                    .height(64.dp)
                    .padding(
                        start = 8.dp
                    )
                    .shimmer(shimmer)
                    .background(
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}

@PreviewLightDark
@Composable
fun MenuScreenLoadingPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MenuScreenLoading(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}