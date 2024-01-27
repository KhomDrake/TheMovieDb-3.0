package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.MoviePoster
import com.vlv.common.ui.extension.handle
import com.vlv.common.ui.shimmer.GridPosterShimmer
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.components.StateView
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.movieState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        favoritesViewModel.moviesFavorites()
    })

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        state.handle(
            success = { data ->
                if(data.isEmpty()) {
                    StateView(
                        icon = com.vlv.imperiya.core.R.drawable.ic_movie_enable,
                        iconTint = MaterialTheme.colorScheme.onBackground,
                        title = stringResource(id = R.string.favorite_movie_empty_state),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        content = {
                            items(data) { movie ->
                                MoviePoster(
                                    movie = movie,
                                    height = 200.dp,
                                    onRouteNavigation = routeNavigation
                                )
                            }
                        }
                    )
                }
            },
            error = {
                SmallWarningView(
                    title = stringResource(id = R.string.favorite_error_state_title),
                    body = null,
                    linkActionText = stringResource(id = com.vlv.ui.R.string.common_error_button),
                    onClickLink = {
                        favoritesViewModel.moviesFavorites()
                    },
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                )
            },
            loading = {
                GridPosterShimmer()
            }
        )
    }
}