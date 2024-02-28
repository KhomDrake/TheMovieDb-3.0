package com.vlv.movie.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.route.MOVIES_LISTING_TYPE_EXTRA
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.common.ui.paging.movie.MOVIE_CONTENT_TYPE
import com.vlv.common.ui.paging.movie.MoviesPagingGrid
import com.vlv.extensions.idInt
import com.vlv.imperiya.core.ui.components.DefaultTopBar
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
            TheMovieDbThemeWithDynamicColors {
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

    MoviesPagingGrid(
        routeNavigation = onRouteNavigation,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding()),
        itemCount = movies.itemCount,
        item = { index -> movies[index] },
        itemKey = movies.itemKey { movie -> "${movie.id}-${idInt()}" },
        itemContentType = movies.itemContentType { MOVIE_CONTENT_TYPE },
        loadStates = movies.loadState,
    )
}
