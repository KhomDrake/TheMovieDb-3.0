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
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.poster.MoviePoster
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.preview.BackgroundPreview
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    emptyStateTitle: String? = null,
    emptyStateBody: String? = null,
    percentage: Float = .8f,
    onClickMovie: RouteNavigation
) {
    val lazyListState = rememberLazyListState()

    if(movies.isEmpty()) {
        StateView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            icon = R.drawable.ic_movie,
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
                    movies,
                    key = { movie -> movie.apiId }
                ) { movie ->
                    MoviePoster(
                        movie = movie,
                        onRouteNavigation = onClickMovie,
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

class MovieCarouselDataPreview(
    val movies: List<Movie>,
    val emptyStateTitle: String? = null,
    val emptyStateBody: String? = null,
    val percentage: Float = .8f
)

class MovieCarouselProvider: PreviewParameterProvider<MovieCarouselDataPreview> {

    override val values: Sequence<MovieCarouselDataPreview>
        get() = listOf(
            MovieCarouselDataPreview(
                listOf(),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            MovieCarouselDataPreview(
                listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        3,
                        "Duna 2",
                        "asda"
                    ),
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            MovieCarouselDataPreview(
                listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna Alone",
                        "asda"
                    )
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
            ),
            MovieCarouselDataPreview(
                listOf(
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        2,
                        "Duna",
                        "asda"
                    ),
                    Movie(
                        false,
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        "/nbrqj9q8WubD3QkYm7n3GhjN7kE.jpg",
                        3,
                        "Duna 2",
                        "asda"
                    ),
                ),
                emptyStateTitle = "Empty title",
                emptyStateBody = "Empty body",
                percentage = 1f
            ),
        ).asSequence()

}

@PreviewLightDark
@Composable
fun MovieCarouselPreview(
    @PreviewParameter(MovieCarouselProvider::class) data: MovieCarouselDataPreview
) {
    TheMovieDbAppTheme {
        BackgroundPreview {
            MovieCarousel(
                movies = data.movies,
                emptyStateTitle = data.emptyStateTitle,
                emptyStateBody = data.emptyStateBody,
                onClickMovie = {_, _ ->},
                percentage = data.percentage
            )
        }
    }
}
