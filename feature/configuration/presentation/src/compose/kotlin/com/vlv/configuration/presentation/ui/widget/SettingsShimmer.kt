package com.vlv.configuration.presentation.ui.widget

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
import br.com.khomdrake.imperiya.ui.preview.BackgroundPreview
import br.com.khomdrake.imperiya.ui.theme.ImperiyaTheme
import com.valentinilk.shimmer.ShimmerBounds
import com.valentinilk.shimmer.rememberShimmer
import com.valentinilk.shimmer.shimmer

@Composable
fun SettingsShimmer(
    modifier: Modifier = Modifier
) {
    val shimmerInstance = rememberShimmer(shimmerBounds = ShimmerBounds.View)

    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(.4f)
                .height(24.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(42.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(.3f)
                .height(20.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(16.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.size(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(.3f)
                .height(20.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
        Spacer(modifier = Modifier.size(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(.6f)
                .height(16.dp)
                .shimmer(shimmerInstance)
                .background(
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                )
        )
    }
}


@PreviewLightDark
@Composable
fun SettingsShimmerPreview() {
    ImperiyaTheme {
        BackgroundPreview {
            SettingsShimmer(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}