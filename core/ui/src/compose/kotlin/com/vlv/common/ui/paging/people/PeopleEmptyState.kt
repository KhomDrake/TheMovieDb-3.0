package com.vlv.common.ui.paging.people

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@Composable
fun PeopleEmptyState(
    modifier: Modifier = Modifier,
    title: String? = stringResource(id = R.string.common_people_empty_view_title_default),
    body: String? = null,
    @DrawableRes
    iconRes: Int = com.vlv.imperiya.core.R.drawable.ic_people,
    contentDescription: String? = null,

) {
    StateView(
        icon = iconRes,
        title = title,
        body = body,
        contentDescriptionIcon = contentDescription,
        iconTint = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@PreviewLightDark
@Composable
fun PeopleEmptyState() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeopleEmptyState(
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}