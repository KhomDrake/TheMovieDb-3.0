package com.vlv.common.ui.cast

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlv.common.data.cast.Cast
import com.vlv.common.data.cast.toPeople
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@Composable
fun CastList(
    castItems: List<Cast>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues = PaddingValues(
        horizontal = 16.dp,
        vertical = 12.dp
    ),
    avatarSize: Dp = 48.dp,
    verticalArrangement: Arrangement.Vertical = Arrangement.spacedBy(12.dp)
) {

    LazyColumn(
        modifier = modifier,
        content = {
            items(castItems) { cast ->
                CastItem(
                    cast = cast,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClickCast = { castClicked ->
                        routeNavigation.invoke(
                            ScreenRoute.PEOPLE_DETAIL,
                            castClicked.toPeople()
                        )
                    },
                    avatarSize = avatarSize
                )
            }
        },
        contentPadding = contentPaddingValues,
        verticalArrangement = verticalArrangement
    )
}

@PreviewLightDark
@Composable
fun CastListPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            CastList(
                castItems = listOf(
                    Cast(
                        castId = 2,
                        character = "J. Robert Oppenheimer",
                        personId = 2,
                        name = "Cillian Murphy",
                        originalName = "Cillian Murphy",
                        knowFor = "Actor",
                        profilePath = null
                    ),
                    Cast(
                        castId = 3,
                        character = "J. Robert Oppenheimer",
                        personId = 23,
                        name = "Cillian Murphy",
                        originalName = "Cillian Murphy",
                        knowFor = "Actor",
                        profilePath = null
                    ),
                    Cast(
                        castId = 4,
                        character = "J. Robert Oppenheimer",
                        personId = 4,
                        name = "Cillian Murphy",
                        originalName = "Cillian Murphy",
                        knowFor = "Actor",
                        profilePath = null
                    ),
                ),
                routeNavigation = { _, _ -> }
            )
        }
    }
}
