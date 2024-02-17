package com.vlv.common.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.vlv.common.data.movie.Movie
import com.vlv.common.route.RouteNavigation

@Composable
fun MovieList(
    movies: List<Movie>,
    routeNavigation: RouteNavigation,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    heightItem: Dp = 200.dp,
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        content = {
            items(movies) { movie ->
                MoviePoster(
                    movie = movie,
                    height = heightItem,
                    onRouteNavigation = routeNavigation
                )
            }
        },
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    )
}