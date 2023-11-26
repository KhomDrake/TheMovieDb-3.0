package com.vlv.movie.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.MoviePoster
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.common.ui.shimmer.SinglePosterShimmer
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.movie.R
import org.koin.androidx.compose.koinViewModel

class MovieListingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type: MovieListType = intent.extras
            ?.getSerializable(
                MOVIES_LISTING_TYPE_EXTRA, MovieListType::class.java
        ) ?: MovieListType.TRENDING
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        val nameRes = when(type) {
                            MovieListType.UPCOMING -> R.string.movie_title_upcoming
                            MovieListType.POPULAR -> R.string.movie_title_popular
                            MovieListType.TOP_RATED -> R.string.movie_title_top_rated
                            MovieListType.NOW_PLAYING -> R.string.movie_title_now_playing
                            MovieListType.TRENDING -> R.string.movie_title_trending_now
                        }
                        DefaultTopBar(title = stringResource(id = nameRes)) {
                            finish()
                        }
                    }
                ) {
                    MovieListing(
                        paddingValues = it,
                        movieListType = type,
                        onRouteNavigation = { route, data ->
                            handleRoute(route, data)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MovieListing(
    paddingValues: PaddingValues,
    movieListType: MovieListType,
    onRouteNavigation: RouteNavigation,
    viewModel: MovieListingViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = movieListType, block = {
        viewModel.movies(movieListType)
    })

    val movies = viewModel.state.collectAsLazyPagingItems()

    if(movies.loadState.refresh is LoadState.Loading) {
        GridPosterShimmer(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding()),
            count = 4,
            height = 200.dp
        )
    } else {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding()
                ),
            columns = GridCells.Fixed(2),
            content = {
                items(
                    count = movies.itemCount,
                    key = movies.itemKey { movie -> movie.id },
                    contentType = movies.itemContentType { "Poster" }
                ) { index ->
                    movies[index]?.let { movie ->
                        MoviePoster(
                            movie = movie,
                            height = 200.dp,
                            onRouteNavigation = onRouteNavigation
                        )
                    }
                }

                item {
                    when(movies.loadState.append) {
                        is LoadState.Loading -> {
                            SinglePosterShimmer(
                                modifier = Modifier.fillMaxWidth(),
                                height = 200.dp
                            )
                        }
                        is LoadState.Error -> {
                            // one item error
                        }
                        else -> Unit
                    }
                }
            },
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        )
    }

}
