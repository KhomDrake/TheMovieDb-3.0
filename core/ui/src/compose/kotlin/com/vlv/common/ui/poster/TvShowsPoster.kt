package com.vlv.common.ui.poster

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.ui.R

@Composable
fun TvShowsPoster(
    tvShow: TvShow,
    onRouteNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    height: Dp = 150.dp,
    loadPoster: Boolean = true
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    MaterialTheme.colorScheme.tertiary,
                    RoundedCornerShape(16.dp)
                )
                .clickable {
                    onRouteNavigation.invoke(ScreenRoute.TV_SHOW_DETAIL, tvShow)
                },
            model = (if(loadPoster) tvShow.posterPath else tvShow.backdropPath)
                ?.toUrlMovieDb(),
            contentDescription = stringResource(
                id = if(loadPoster)
                    R.string.common_series_poster_content_description
                else
                    R.string.common_series_backdrop_content_description
            )
        )

        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = tvShow.title,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )
    }
}

@PreviewLightDark
@PreviewFontScale
@Composable
fun TvShowsPosterPreview() {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowsPoster(
                tvShow = TvShow(
                    false,
                    2,
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                    "Duna"
                ),
                onRouteNavigation = {_,_ ->}
            )
        }
    }
}