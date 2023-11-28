package com.vlv.themoviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.favorite.presentation.ui.FavoritesScreen
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.themoviedb.R
import com.vlv.themoviedb.ui.menu.MenuScreen
import com.vlv.themoviedb.ui.movie.MovieScreen
import com.vlv.themoviedb.ui.series.SeriesScreen
import com.vlv.imperiya.core.R as RImperiya

enum class MainScreens {
    MOVIE,
    SERIES,
    SEARCH,
    FAVORITES,
    MENU
}

class BottomNavigationItems(
    val mainScreens: MainScreens,
    val title: String,
    @DrawableRes
    val selectedIcon: Int,
    @DrawableRes
    val unselectedIcon: Int
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                MainScreen { route, data ->
                    handleRoute(route, data)
                }
            }
        }
    }

}

@Composable
fun MainScreen(
    onNavigate: RouteNavigation
) {
    val items = listOf(
        BottomNavigationItems(
            mainScreens = MainScreens.MOVIE,
            title = stringResource(R.string.movie_title),
            selectedIcon = RImperiya.drawable.ic_movie_enable,
            unselectedIcon = RImperiya.drawable.ic_movie_disable
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.SERIES,
            title = stringResource(R.string.series_title),
            selectedIcon = RImperiya.drawable.ic_series_enable,
            unselectedIcon = RImperiya.drawable.ic_series_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.SEARCH,
            title = stringResource(R.string.search_title),
            selectedIcon = RImperiya.drawable.ic_search_enable,
            unselectedIcon = RImperiya.drawable.ic_search_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.FAVORITES,
            title = stringResource(R.string.favorites_title),
            selectedIcon = RImperiya.drawable.ic_heart_enable,
            unselectedIcon = RImperiya.drawable.ic_heart_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.MENU,
            title = stringResource(R.string.menu_title),
            selectedIcon = RImperiya.drawable.ic_options_enable,
            unselectedIcon = RImperiya.drawable.ic_options_enable,
        ),
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        var selectedIndex: Int by rememberSaveable {
            mutableIntStateOf(0)
        }
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selectedIndex,
                            onClick = {
                                selectedIndex = item.mainScreens.ordinal
                            },
                            icon = {
                                Icon(
                                    modifier = Modifier
                                        .background(Color.Transparent),
                                    painter = painterResource(
                                        id = if(index == selectedIndex)
                                            item.selectedIcon
                                        else
                                            item.unselectedIcon
                                    ),
                                    contentDescription = item.title,
                                    tint = MaterialTheme.colorScheme.tertiary
                                )
                            },
                            alwaysShowLabel = false,
                            label = {
                                Text(
                                    text = item.title,
                                    style = TheMovieDbTypography.ParagraphBoldStyle,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        )
                    }
                }
            }
        ) { paddingValues ->
            when(selectedIndex) {
                MainScreens.MOVIE.ordinal -> {
                    MovieScreen(paddingValues, onNavigate)
                }
                MainScreens.MENU.ordinal -> {
                    MenuScreen(paddingValues, onNavigate = onNavigate)
                }
                MainScreens.SERIES.ordinal -> {
                    SeriesScreen(paddingValues, onNavigate)
                }
                MainScreens.FAVORITES.ordinal -> {
                    FavoritesScreen(
                        routeNavigation = onNavigate,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = paddingValues.calculateBottomPadding())
                    )
                }
                else -> Unit
            }
        }
    }
}

