package com.vlv.themoviedb.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.data.database.data.ItemType
import com.vlv.favorite.presentation.ui.FavoritesScreen
import com.vlv.imperiya.core.ui.components.FilterItemData
import com.vlv.search.R
import com.vlv.search.ui.SearchScreen
import com.vlv.themoviedb.ui.menu.MenuScreen
import com.vlv.themoviedb.ui.movie.MovieScreen
import com.vlv.themoviedb.ui.series.SeriesScreen

@Composable
fun MainScreenContent(
    selectedIndex: MainScreens,
    paddingValues: PaddingValues,
    onNavigate: RouteNavigation
) {
    when(selectedIndex) {
        MainScreens.MOVIE -> {
            MovieScreen(paddingValues, onNavigate)
        }
        MainScreens.MENU -> {
            MenuScreen(paddingValues, onNavigate = onNavigate)
        }
        MainScreens.TV_SHOW -> {
            SeriesScreen(paddingValues, onNavigate)
        }
        MainScreens.FAVORITES -> {
            FavoritesScreen(
                routeNavigation = onNavigate,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding())
            )
        }
        MainScreens.SEARCH -> {
            SearchScreen(
                routeNavigation = onNavigate,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = paddingValues.calculateBottomPadding()),
                filters = listOf(
                    FilterItemData(
                        ItemType.MOVIE.ordinal,
                        stringResource(id = R.string.search_movie_option),
                        ItemType.MOVIE.name
                    ),
                    FilterItemData(
                        ItemType.TV_SHOW.ordinal,
                        stringResource(id = R.string.search_series_option),
                        ItemType.TV_SHOW.name
                    ),
                    FilterItemData(
                        ItemType.PEOPLE.ordinal,
                        stringResource(id = R.string.search_people_option),
                        ItemType.PEOPLE.name
                    ),
                )
            )
        }
    }
}
