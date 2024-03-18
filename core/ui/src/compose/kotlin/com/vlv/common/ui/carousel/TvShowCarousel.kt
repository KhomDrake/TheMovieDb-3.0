package com.vlv.common.ui.carousel

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.poster.TvShowsPoster
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TvShowCarousel(
    modifier: Modifier = Modifier,
    tvShows: List<TvShow>,
    emptyStateTitle: String? = null,
    emptyStateBody: String? = null,
    percentage: Float = .8f,
    onClickSeries: RouteNavigation
) {
    val lazyListState = rememberLazyListState()

    if(tvShows.isEmpty()) {
        StateView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            icon = R.drawable.ic_tv,
            title = emptyStateTitle,
            body = emptyStateBody,
            iconTint = MaterialTheme.colorScheme.onBackground
        )
    } else {
        LazyRow(
            modifier = modifier
                .fillMaxWidth(),
            content = {
                items(
                    tvShows,
                    key = { item -> item }
                ) { item ->
                    TvShowsPoster(
                        tvShow = item,
                        onRouteNavigation = onClickSeries,
                        modifier = Modifier
                            .fillParentMaxWidth(percentage),
                        loadPoster = false
                    )
                }
            },
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        )
    }
}

class SeriesCarouselDataPreview(
    val tvShows: List<TvShow>,
    val emptyStateTitle: String? = null,
    val emptyStateBody: String? = null,
    val percentage: Float = .8f
)

class SeriesCarouselProvider: PreviewParameterProvider<SeriesCarouselDataPreview> {

    override val values: Sequence<SeriesCarouselDataPreview>
        get() = listOf(
            SeriesCarouselDataPreview(
                listOf(),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            SeriesCarouselDataPreview(
                listOf(
                    TvShow(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    TvShow(
                        false,
                        3,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 2"
                    ),
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            SeriesCarouselDataPreview(
                listOf(
                    TvShow(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            SeriesCarouselDataPreview(
                listOf(
                    TvShow(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    TvShow(
                        false,
                        3,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna 2"
                    ),
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
                percentage = 1f
            ),
        ).asSequence()

}

@PreviewLightDark
@PreviewFontScale
@Composable
fun SeriesCarouselPreview(
    @PreviewParameter(SeriesCarouselProvider::class) data: SeriesCarouselDataPreview
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            TvShowCarousel(
                tvShows = data.tvShows,
                percentage = data.percentage,
                emptyStateTitle = data.emptyStateTitle,
                emptyStateBody = data.emptyStateBody,
                onClickSeries = {_, _ ->}
            )
        }
    }
}
