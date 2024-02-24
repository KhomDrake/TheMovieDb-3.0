package com.vlv.genre.presentation.ui.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.genre.R
import com.vlv.genre.presentation.ui.ByGenreContent
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import org.koin.androidx.compose.koinViewModel

class MovieGenreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbThemeWithDynamicColors {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = stringResource(id = R.string.genre_movie_toolbar_title)) {
                            finish()
                        }
                    }
                ) {
                    TabScreen(
                        paddingValues = it,
                        routeNavigation = { route, data ->
                            handleRoute(route, data)
                        }
                    )
                }
            }
        }
    }

}

@Composable
fun TabScreen(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    viewModel: MovieGenreViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadGenres()
    })

    ByGenreContent(
        state = state,
        tabContent = { genre ->
            MoviesByGenre(
                genre = genre,
                routeNavigation = routeNavigation
            )
        },
        paddingValues = paddingValues,
        tryAgain = {
            viewModel.loadGenres()
        }
    )
}