package com.vlv.common.ui.paging.people

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun PeopleErrorState(
    modifier: Modifier = Modifier,
    title: String? = stringResource(id = R.string.common_error_title_load_more),
    body: String? = stringResource(id = R.string.common_error_description_load),
    linkActionText: String = stringResource(id = R.string.common_error_button_load),
    onClickLink: (() -> Unit)? = null
) {
    SmallWarningView(
        title = title,
        body = body,
        linkActionText = linkActionText,
        onClickLink = onClickLink,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun PeopleErrorStatePrev() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeopleErrorState(
                modifier = Modifier
                    .fillMaxWidth(),
                title = "Failed to load more",
                body = "Check your internet connection, wait a few moments and click in load again",
                linkActionText = "Load again",
            )
        }
    }
}