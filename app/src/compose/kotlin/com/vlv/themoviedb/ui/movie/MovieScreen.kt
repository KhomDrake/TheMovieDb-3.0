package com.vlv.themoviedb.ui.movie

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.toMovieSearch
import com.vlv.data.local.datastore.DataVault
import com.vlv.data.network.database.data.ImageType
import com.vlv.imperiya.core.ui.components.SearchComponent
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.theme.Link
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.movie.widget.MovieCarousel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(onIntent: (Intent) -> Unit) {
    val movieSearch = LocalContext.current.toMovieSearch()

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        SearchComponent(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            hint = "Search for movies",
            onFocus = {
                onIntent.invoke(movieSearch)
            }
        )
        NowPlaying(
            onIntent = {
//            onIntent.invoke()
            }
        )
        Trending(
            onIntent = {

            }
        )
    }
}

@Composable
fun NowPlaying(
    movieViewModel: NowPlayingViewModel = koinViewModel(),
    onIntent: (Movie) -> Unit
) {
    val movieNowPlaying by movieViewModel.state.collectAsState()

    MovieInformation(
        title = stringResource(id = R.string.now_playing_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_movie_now_playing),
        data = movieNowPlaying,
        onIntent = onIntent,
        titleWarning = stringResource(id = R.string.error_movie_load_text_title_now_playing),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button)
    )
}

@Composable
fun Trending(
    movieViewModel: TrendingViewModel = koinViewModel(),
    onIntent: (Movie) -> Unit
) {
    val movieNowPlaying by movieViewModel.state.collectAsState()

    MovieInformation(
        title = stringResource(id = R.string.trending_title),
        emptyStateTitle = stringResource(id = R.string.empty_state_text_movie_trending),
        data = movieNowPlaying,
        onIntent = onIntent,
        titleWarning = stringResource(id = R.string.error_movie_load_text_title_trending),
        bodyWarning = stringResource(id = com.vlv.ui.R.string.common_error_description),
        linkTextWarning = stringResource(id = com.vlv.ui.R.string.common_error_button)
    )
}


@Composable
fun MovieInformation(
    title: String,
    emptyStateTitle: String,
    titleWarning: String,
    bodyWarning: String,
    linkTextWarning: String,
    data: Response<List<Movie>>,
    onIntent: (Movie) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 20.dp
            ),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = title,
            style = TheMovieDbTypography.TitleStyle,
            color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = stringResource(id = R.string.see_all_text),
            style = TheMovieDbTypography.LinkStyle,
            color = Link
        )
    }


    when(data.state) {
        ResponseStatus.SUCCESS -> {
            val movies = data.data ?: return
            MovieCarousel(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                movies = movies,
                emptyStateTitle = emptyStateTitle,
                onClickMovie = {
                    onIntent.invoke(it)
                }
            )
        }
        ResponseStatus.LOADING -> {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = "LOADING",
                style = TheMovieDbTypography.TitleBigStyle,
                color = Link
            )
        }
        ResponseStatus.ERROR -> {
            SmallWarningView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                title = titleWarning,
                body = bodyWarning,
                linkActionText = linkTextWarning
            )
        }
        else -> Unit
    }
}

fun String.toUrlMovieDb(imageType: ImageType = ImageType.POSTER) = runCatching {
    "${DataVault.cachedDataString(ImageType.SECURE_BASE_URL.name)}${
        DataVault.cachedDataString(
            imageType.name
        )
    }$this"
}.getOrNull()