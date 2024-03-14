package com.vlv.movie.presentation.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.DETAIL_OBJECT_EXTRA
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.extensions.parcelable
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import org.koin.androidx.compose.koinViewModel

class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbThemeWithDynamicColors {
                val movie = intent.extras?.parcelable<DetailObject>(DETAIL_OBJECT_EXTRA)
                    ?: return@TheMovieDbThemeWithDynamicColors finish()
                Scaffold(
                    topBar = {
                        TopBar(
                            movie = movie,
                            onBackButton = {
                                finish()
                            }
                        )
                    }
                ) {
                    MovieDetailContent(
                        detailObject = movie,
                        routeNavigation = { route, data -> handleRoute(route, data) },
                        paddingValues = it
                    )
                }
            }
        }
    }

}

@Composable
fun TopBar(
    movie: DetailObject,
    onBackButton: () -> Unit,
    viewModel: MovieDetailViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = movie.id, block = {
        viewModel.isFavorite(movie)
    })

    val isFavorite by viewModel.favoriteState.collectAsState()

    DefaultTopBar(
        title = movie.title,
        actions = {
            IconButton(onClick = {
                viewModel.changeFavorite(movie, isFavorite)
            }) {
                Icon(
                    painter = painterResource(
                        id = if(isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_heart_enable
                    ),
                    contentDescription = stringResource(
                        id = if(isFavorite) com.vlv.movie.R.string.movie_is_favorite
                            else com.vlv.movie.R.string.movie_is_not_favorite
                    ),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        onBackButton = {
            onBackButton.invoke()
        }
    )
}