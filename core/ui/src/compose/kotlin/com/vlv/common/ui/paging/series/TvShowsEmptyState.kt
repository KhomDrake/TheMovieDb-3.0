package com.vlv.common.ui.paging.series

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun TvShowsEmptyState(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.common_empty_view_title_default)
) {
    StateView(
        icon = com.vlv.imperiya.core.R.drawable.ic_tv,
        iconTint = MaterialTheme.colorScheme.onBackground,
        title = title,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun TvShowsEmptyStatePreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsEmptyState(
                title = "No tv show was found",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}
