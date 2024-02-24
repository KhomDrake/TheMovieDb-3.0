package com.vlv.genre.presentation.ui.tvshow

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

class TvShowsGenreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbThemeWithDynamicColors {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = stringResource(id = R.string.genre_tv_show_toolbar_title)) {
                            finish()
                        }
                    }
                ) {
                    TvShowsTabScreen(
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
fun TvShowsTabScreen(
    paddingValues: PaddingValues,
    routeNavigation: RouteNavigation,
    viewModel: TvShowsGenreViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.loadGenres()
    })

    val state by viewModel.state.collectAsState()

    ByGenreContent(
        paddingValues = paddingValues,
        state = state,
        tabContent = { item ->
            TvShowsByGenre(
                genre = item,
                routeNavigation = routeNavigation
            )
        },
        tryAgain = {
            viewModel.loadGenres()
        }
    )
}
