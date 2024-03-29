package com.vlv.common.ui.poster

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlv.common.data.people.People
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun PeoplePoster(
    people: People,
    onRouteNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(size)
                .clip(
                    RoundedCornerShape(size / 2)
                )
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    RoundedCornerShape(size / 2)
                )
                .clickable {
                    onRouteNavigation.invoke(ScreenRoute.PEOPLE_DETAIL, people)
                },
            model = people.profilePath?.toUrlMovieDb(),
            contentDescription = stringResource(
                id = R.string.common_people_avatar_content_description
            )
        )

        Text(
            text = people.name,
            color = MaterialTheme.colorScheme.onBackground,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp)
        )
    }

}

@Preview
@Composable
fun PeoplePosterPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            PeoplePoster(
                people = People(
                    2,
                    "Walter White",
                    "",
                    ""
                ),
                modifier = Modifier
                    .fillMaxWidth(.8f),
                onRouteNavigation = { _, _ -> }
            )
        }
    }
}