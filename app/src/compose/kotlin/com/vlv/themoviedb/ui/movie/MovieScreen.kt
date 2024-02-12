package com.vlv.themoviedb.ui.movie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.ScreenRoute
import com.vlv.common.ui.MovieCarousel
import com.vlv.common.ui.shimmer.CarouselShimmer
import com.vlv.favorite.presentation.ui.movie.MovieCarouselFavorite
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.themoviedb.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(
    paddingValues: PaddingValues,
    onNavigate: RouteNavigation
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                bottom = paddingValues.calculateBottomPadding()
            )
            .verticalScroll(scrollState)
    ) {
        SearchComponent(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Search for movies",
            onClick = {
                onNavigate.invoke(ScreenRoute.MOVIE_SEARCH, null)
            },
            enable = false
        )
        Trending(onNavigate = onNavigate)
        NowPlaying(onNavigate = onNavigate)
        SeeAll(
            title = stringResource(id = R.string.favorites_title),
            onClickSeeAll = {
                onNavigate.invoke(ScreenRoute.FAVORITES_MOVIE, null)
            }
        )
        MovieCarouselFavorite(
            modifier = Modifier
                .padding(16.dp),
            onClickMovie = onNavigate,
            errorTitle = stringResource(id = R.string.error_movie_load_text_title_favorites),
            errorBody = stringResource(id = com.vlv.ui.R.string.common_error_description),
            errorLink = stringResource(id = com.vlv.ui.R.string.common_error_button),
            emptyStateTitle = stringResource(id = R.string.empty_state_text_movie_favorite)
        )
    }
}

@Composable
fun NowPlaying(
    movieViewModel: NowPlayingViewModel = koinViewModel(),
    onNavigate: RouteNavigation
) {
    val movieNowPlaying by movieViewModel.state.collectAsState()

    MovieInformation(
        title = stringResource(id = R.string.now_playing_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_movie_now_playing),
        data = movieNowPlaying,
        onNavigate = onNavigate,
        titleWarning = stringResource(id = R.string.error_movie_load_text_title_now_playing),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button),
        onError = {
            movieViewModel.nowPlaying()
        },
        onNavigateSeeAll = {
            onNavigate.invoke(ScreenRoute.MOVIE_NOW_PLAYING, null)
        }
    )
}

@Composable
fun Trending(
    movieViewModel: TrendingViewModel = koinViewModel(),
    onNavigate: RouteNavigation
) {
    val movieTrending by movieViewModel.state.collectAsState()

    MovieInformation(
        title = stringResource(id = R.string.trending_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_movie_trending),
        data = movieTrending,
        onNavigate = onNavigate,
        titleWarning = stringResource(id = R.string.error_movie_load_text_title_trending),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button),
        onError = {
            movieViewModel.trending()
        },
        onNavigateSeeAll = {
            onNavigate.invoke(ScreenRoute.MOVIE_TRENDING, null)
        },
        percentage = 1f
    )
}

@Composable
fun SeeAll(
    title: String,
    onClickSeeAll: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp
            )
    ) {
        Text(
            text = title,
            style = TheMovieDbTypography.TitleStyle,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.see_all_text),
            style = TheMovieDbTypography.LinkStyle,
            color = Link,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onClickSeeAll.invoke()
                }
                .padding(start = 8.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
fun MovieInformation(
    title: String,
    emptyStateTitle: String,
    titleWarning: String,
    bodyWarning: String,
    linkTextWarning: String,
    data: Response<List<Movie>>,
    onNavigate: RouteNavigation,
    onNavigateSeeAll: () -> Unit,
    percentage: Float = .8f,
    onError: () -> Unit
) {
    SeeAll(
        title = title,
        onClickSeeAll = onNavigateSeeAll
    )
    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val movies = data.data ?: return
            MovieCarousel(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                movies = movies,
                percentage = percentage,
                emptyStateTitle = emptyStateTitle,
                onClickMovie = {
                    onNavigate.invoke(ScreenRoute.MOVIE_DETAIL, it)
                }
            )
        }
        ResponseStatus.LOADING -> {
            CarouselShimmer()
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                title = titleWarning,
                body = bodyWarning,
                linkActionText = linkTextWarning,
                onClickLink = onError
            )
        }
        else -> Unit
    }
}