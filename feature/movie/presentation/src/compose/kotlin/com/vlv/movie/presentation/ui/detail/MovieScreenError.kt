package com.vlv.movie.presentation.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.imperiya.core.ui.components.WarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun MovieScreenError(
    modifier: Modifier = Modifier,
    onTryAgain: () -> Unit = {},
    title: String = stringResource(id = R.string.common_error_title),
    body: String = stringResource(id = R.string.common_error_description),
    linkActionText: String = stringResource(id = R.string.common_error_button),
) {
    WarningView(
        title = title,
        body = body,
        linkActionText = linkActionText,
        onClickLink = onTryAgain,
        showCloseIcon = false,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun MovieScreenErrorPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieScreenError(
                title = "Text",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}
