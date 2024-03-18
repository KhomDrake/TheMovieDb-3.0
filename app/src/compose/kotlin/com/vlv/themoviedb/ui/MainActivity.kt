package com.vlv.themoviedb.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import com.vlv.common.route.RouteNavigation
import com.vlv.common.route.handleRoute
import com.vlv.common.ui.extension.TheMovieDbThemeWithDynamicColors
import com.vlv.themoviedb.R

enum class MainScreens {
    MOVIE,
    TV_SHOW,
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
            TheMovieDbThemeWithDynamicColors {
                MainScreen(
                    onNavigate = { route, data ->
                        handleRoute(route, data)
                    }
                )
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
            selectedIcon = R.drawable.ic_movie_enable,
            unselectedIcon = R.drawable.ic_movie_disable
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.TV_SHOW,
            title = stringResource(R.string.tv_show_title),
            selectedIcon = R.drawable.ic_series_enable,
            unselectedIcon = R.drawable.ic_series_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.SEARCH,
            title = stringResource(R.string.search_title),
            selectedIcon = R.drawable.ic_search_enable,
            unselectedIcon = R.drawable.ic_search_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.FAVORITES,
            title = stringResource(R.string.favorites_title),
            selectedIcon = R.drawable.ic_heart_enable,
            unselectedIcon = R.drawable.ic_heart_disable,
        ),
        BottomNavigationItems(
            mainScreens = MainScreens.MENU,
            title = stringResource(R.string.menu_title),
            selectedIcon = R.drawable.ic_options_enable,
            unselectedIcon = R.drawable.ic_options_enable,
        ),
    )

    var selectedIndex by rememberSaveable {
        mutableStateOf(MainScreens.MOVIE)
    }
    Scaffold(
        bottomBar = {
            MainScreenNavBar(
                items = items,
                selectedIndex = selectedIndex,
                onSelect = {
                    selectedIndex = it
                }
            )
        }
    ) { paddingValues ->
        MainScreenContent(
            selectedIndex = selectedIndex,
            paddingValues = paddingValues,
            onNavigate = onNavigate
        )
    }
}

