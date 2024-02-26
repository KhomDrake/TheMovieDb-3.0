package com.vlv.tv_show.presentation.ui.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vlv.imperiya.core.ui.components.WarningView
import com.vlv.ui.R

@Composable
fun TvShowsScreenError(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.common_error_title),
    body: String = stringResource(id = R.string.common_error_description),
    linkActionText: String = stringResource(id = R.string.common_error_button),
    onTryAgain: () -> Unit = {}
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
