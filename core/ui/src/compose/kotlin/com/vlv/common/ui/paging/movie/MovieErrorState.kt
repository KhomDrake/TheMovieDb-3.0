package com.vlv.common.ui.paging.movie

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun MovieErrorState(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.common_error_title),
    body: String = stringResource(id = R.string.common_error_description),
    linkActionText: String = stringResource(id = R.string.common_error_button),
    onTryAgain: (() -> Unit)? = null,
) {
    SmallWarningView(
        title = title,
        body = body,
        linkActionText = linkActionText,
        onClickLink = onTryAgain,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun MovieErrorStatePreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieErrorState(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}