package com.vlv.favorite.presentation.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.common.ui.extension.handle
import com.vlv.favorite.R
import com.vlv.imperiya.core.ui.components.StateView
import org.koin.androidx.compose.koinViewModel

@Composable
fun PeopleFavorites(
    routeNavigation: RouteNavigation,
    favoritesViewModel: FavoritesViewModel = koinViewModel()
) {
    val state by favoritesViewModel.peopleState.collectAsState()

    LaunchedEffect(
        key1 = Unit,
        block = {
            favoritesViewModel.peopleFavorites()
        }
    )

    state.handle(
        success = { data ->
            if(data.isEmpty()) {
                StateView(
                    icon = com.vlv.imperiya.core.R.drawable.ic_people,
                    iconTint = MaterialTheme.colorScheme.onBackground,
                    title = stringResource(id = R.string.favorite_people_empty_state),
                    modifier = Modifier
                        .fillMaxWidth()
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    content = {
                        items(data) { people ->
                            Text(text = people.name)
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