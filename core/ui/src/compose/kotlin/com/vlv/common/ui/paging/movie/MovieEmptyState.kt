package com.vlv.common.ui.paging.movie

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.ui.R
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun MovieEmptyState(
    title: String,
    modifier: Modifier = Modifier
) {
    StateView(
        icon = R.drawable.ic_movie_enable,
        iconTint = MaterialTheme.colorScheme.onBackground,
        title = title,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun MovieEmptyStatePreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieEmptyState(
                title = "None movie was found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}