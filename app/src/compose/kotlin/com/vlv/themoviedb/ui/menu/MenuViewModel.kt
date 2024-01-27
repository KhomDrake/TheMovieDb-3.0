package com.vlv.themoviedb.ui.menu

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.route.ScreenRoute
import com.vlv.themoviedb.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random

enum class MenuItemType {
    HEADER,
    ITEM
}

class MenuItem(
    @StringRes
    val title: Int,
    @DrawableRes
    val icon: Int? = null,
    val type: MenuItemType = MenuItemType.ITEM,
    val action: ScreenRoute? = null,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

class MenuViewModel : ViewModel() {

    private val _state = MutableResponseStateFlow<List<MenuItem>>()

    val state: ResponseStateFlow<List<MenuItem>>
        get() = _state.asResponseStateFlow()

    private fun userItems() = listOf(
        MenuItem(
            R.string.menu_title_user,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_user_option_favorite,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_review,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_session,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_settings,
            action = ScreenRoute.SETTINGS
        )
    )

    private fun moviesItems() = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_movie,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_movie_option_trending,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_TRENDING
        ),
        MenuItem(
            R.string.menu_title_movie_option_top_rated,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_TOP_RATED
        ),
        MenuItem(
            R.string.menu_title_movie_option_now_playing,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_NOW_PLAYING
        ),
        MenuItem(
            R.string.menu_title_movie_option_upcoming,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_UPCOMING
        ),
        MenuItem(
            R.string.menu_title_movie_option_popular,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_POPULAR
        ),
        MenuItem(
            R.string.menu_title_movie_option_genres,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = ScreenRoute.MOVIE_GENRE
        ),
        MenuItem(
            R.string.menu_title_movie_option_discover,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = ScreenRoute.MOVIE_SEARCH
        ),
        MenuItem(
            R.string.menu_title_movie_option_certifications,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie
        ),
    )

    private fun seriesItems() = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_series,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_series_option_trending,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_TRENDING
        ),
        MenuItem(
            R.string.menu_title_series_option_top_rated,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_TOP_RATED
        ),
        MenuItem(
            R.string.menu_title_series_option_airing_today,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_AIRING_TODAY
        ),
        MenuItem(
            R.string.menu_title_series_option_on_the_air,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_ON_THE_AIR
        ),
        MenuItem(
            R.string.menu_title_series_option_popular,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_POPULAR
        ),
        MenuItem(
            R.string.menu_title_series_option_genres,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = ScreenRoute.SERIES_GENRE
        ),
        MenuItem(
            R.string.menu_title_series_option_discover,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = ScreenRoute.SERIES_SEARCH
        ),
        MenuItem(
            R.string.menu_title_series_option_certifications,
            type = MenuItemType.ITEM
        ),
    )

    private fun peopleItems() = listOf(
        MenuItem(
            R.string.menu_title_people,
            type = MenuItemType.HEADER,
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_popular,
            type = MenuItemType.ITEM,
            action = ScreenRoute.PEOPLE_POPULAR,
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_trending,
            type = MenuItemType.ITEM,
            action = ScreenRoute.PEOPLE_TRENDING,
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_search,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = ScreenRoute.PEOPLE_SEARCH
        ),
        MenuItem(
            R.string.menu_title_people_option_movie_credit,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie
        ),
        MenuItem(
            R.string.menu_title_people_option_tv_credit,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv
        )
    )

    fun menuItems() {
        viewModelScope.launch {
            bondsmith<List<MenuItem>>("MENU")
                .config {
                    request {
                        mutableListOf<MenuItem>().apply {
                            addAll(userItems())
                            addAll(moviesItems())
                            addAll(seriesItems())
                            addAll(peopleItems())
                        }.filter { it.action != null || it.type == MenuItemType.HEADER }.toList()
                    }
                }
                .execute()
                .responseStateFlow
                .collectLatest {
                    _state.emit(it)
                }
        }

    }

}