package com.vlv.themoviedb.ui.movie.widget

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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlv.common.data.movie.Movie
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.themoviedb.ui.movie.toUrlMovieDb

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movies: List<Movie>,
    emptyStateTitle: String? = null,
    emptyStateBody: String? = null,
    onClickMovie: (Movie) -> Unit
) {
    val lazyListState = rememberLazyListState()

    if(movies.isEmpty()) {
        StateView(
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
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
            itemsIndexed(movies) { index, movie ->
                Column(
                    modifier = Modifier
                        .fillParentMaxWidth(.8f)
                        .padding(
                            start = if(index == 0) 16.dp else 8.dp,
                            end = if(index == movies.size - 1) 16.dp else 8.dp,
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
                                onClickMovie.invoke(movie)
                            },
                        model = movie.backdropPath?.toUrlMovieDb(),
                        contentDescription = null
                    )

                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .align(CenterHorizontally),
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