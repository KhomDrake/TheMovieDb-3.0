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
import com.vlv.common.route.DETAIL_OBJECT_EXTRA
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.DetailObject
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

class MovieDetailActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                val movie = intent.extras?.getParcelable(
                    DETAIL_OBJECT_EXTRA, DetailObject::class.java
                ) ?: return@TheMovieDbAppTheme finish()
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
                    contentDescription = "Favorite",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        },
        onBackButton = {
            onBackButton.invoke()
        }
    )
}