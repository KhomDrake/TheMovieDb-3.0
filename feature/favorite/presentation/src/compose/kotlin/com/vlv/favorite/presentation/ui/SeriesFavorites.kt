package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
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
import com.vlv.common.ui.SeriesPoster
import com.vlv.common.ui.extension.handle
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.components.StateView
import org.koin.androidx.compose.koinViewModel

@Composable
fun SeriesFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.seriesState.collectAsState()

    LaunchedEffect(key1 = Unit, block = {
        favoritesViewModel.seriesFavorites()
    })

    state.handle(
        success = { data ->
            if(data.isEmpty()) {
                StateView(
                    icon = com.vlv.imperiya.core.R.drawable.ic_tv,
                    iconTint = MaterialTheme.colorScheme.onBackground,
                    title = stringResource(id = R.string.favorite_series_empty_state),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    content = {
                        items(data) { series ->
                            SeriesPoster(
                                series = series,
                                height = 200.dp,
                                onRouteNavigation = routeNavigation
                            )
                        }
                    }
                )
            }
        },
        error = {

        },
        loading = {

        }
    )


}