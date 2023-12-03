package com.vlv.common.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlv.common.data.series.Series
import com.vlv.common.extension.toUrlMovieDb
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesCarousel(
    modifier: Modifier = Modifier,
    series: List<Series>,
    emptyStateTitle: String? = null,
    emptyStateBody: String? = null,
    percentage: Float = .8f,
    onClickSeries: (Series) -> Unit
) {
    val lazyListState = rememberLazyListState()

    if(series.isEmpty()) {
        StateView(
            modifier = Modifier
                .fillMaxWidth(),
            icon = R.drawable.ic_movie,
            title = emptyStateTitle,
            body = emptyStateBody
        )
        return
    }

    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        content = {
            itemsIndexed(series) { index, movie ->
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth(percentage)
                        .padding(
                            start = if (index == 0) 16.dp else 8.dp,
                            end = if (index == series.size - 1) 16.dp else 8.dp,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AsyncImage(
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(
                                RoundedCornerShape(16.dp)
                            )
                            .background(
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable {
                                onClickSeries.invoke(movie)
                            },
                        model = movie.backdropPath?.toUrlMovieDb(),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(Alignment.CenterHorizontally),
                        text = movie.title,
                        style = TheMovieDbTypography.SubTitleBoldStyle,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        },
        state = lazyListState,
        flingBehavior = rememberSnapFlingBehavior(lazyListState)
    )
}

class SeriesCarouselDataPreview(
    val series: List<Series>,
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
                    Series(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    Series(
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
                    Series(
                        false,
                        2,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "Duna"
                    ),
                    Series(
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
            SeriesCarousel(
                series = data.series,
                percentage = data.percentage,
                emptyStateTitle = data.emptyStateTitle,
                emptyStateBody = data.emptyStateBody,
                onClickSeries = {}
            )
        }
    }
}
