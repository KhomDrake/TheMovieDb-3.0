package com.vlv.themoviedb.ui.menu

import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.common.route.toMovieGenre
import com.vlv.common.route.toMovieNowPlaying
import com.vlv.common.route.toMoviePopular
import com.vlv.common.route.toMovieSearch
import com.vlv.common.route.toMovieTopRated
import com.vlv.common.route.toMovieTrending
import com.vlv.common.route.toMovieUpcoming
import com.vlv.common.route.toPeoplePopular
import com.vlv.common.route.toPeopleSearch
import com.vlv.common.route.toPeopleTrending
import com.vlv.common.route.toSeriesAiringToday
import com.vlv.common.route.toSeriesGenre
import com.vlv.common.route.toSeriesOnTheAir
import com.vlv.common.route.toSeriesPopular
import com.vlv.common.route.toSeriesSearch
import com.vlv.common.route.toSeriesTopRated
import com.vlv.common.route.toSeriesTrendingNow
import com.vlv.common.route.toSettings
import com.vlv.themoviedb.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    val action: Intent? = null,
    val id: Int = Random.nextInt(Int.MIN_VALUE, Int.MAX_VALUE)
)

class MenuViewModel() : ViewModel() {

    private val _state = MutableStateFlow<List<MenuItem>>(listOf())

    val state: StateFlow<List<MenuItem>>
        get() = _state.asStateFlow()

    private fun userItems(context: Context) = listOf<MenuItem>(
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
            action = context.toSettings()
        )
    )

    private fun moviesItems(context: Context) = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_movie,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_movie_option_trending,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMovieTrending()
        ),
        MenuItem(
            R.string.menu_title_movie_option_top_rated,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMovieTopRated()
        ),
        MenuItem(
            R.string.menu_title_movie_option_now_playing,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMovieNowPlaying()
        ),
        MenuItem(
            R.string.menu_title_movie_option_upcoming,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMovieUpcoming()
        ),
        MenuItem(
            R.string.menu_title_movie_option_popular,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMoviePopular()
        ),
        MenuItem(
            R.string.menu_title_movie_option_genres,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie,
            action = context.toMovieGenre()
        ),
        MenuItem(
            R.string.menu_title_movie_option_discover,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = context.toMovieSearch()
        ),
        MenuItem(
            R.string.menu_title_movie_option_certifications,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_movie
        ),
    )

    private fun seriesItems(context: Context) = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_series,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_series_option_trending,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesTrendingNow()
        ),
        MenuItem(
            R.string.menu_title_series_option_top_rated,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesTopRated()
        ),
        MenuItem(
            R.string.menu_title_series_option_airing_today,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesAiringToday()
        ),
        MenuItem(
            R.string.menu_title_series_option_on_the_air,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesOnTheAir()
        ),
        MenuItem(
            R.string.menu_title_series_option_popular,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesPopular()
        ),
        MenuItem(
            R.string.menu_title_series_option_genres,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_tv,
            action = context.toSeriesGenre()
        ),
        MenuItem(
            R.string.menu_title_series_option_discover,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = context.toSeriesSearch()
        ),
        MenuItem(
            R.string.menu_title_series_option_certifications,
            type = MenuItemType.ITEM
        ),
    )

    private fun peopleItems(context: Context) = listOf(
        MenuItem(
            R.string.menu_title_people,
            type = MenuItemType.HEADER,
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_popular,
            type = MenuItemType.ITEM,
            action = context.toPeoplePopular(),
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_trending,
            type = MenuItemType.ITEM,
            action = context.toPeopleTrending(),
            icon = com.vlv.imperiya.core.R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_search,
            type = MenuItemType.ITEM,
            icon = com.vlv.imperiya.core.R.drawable.ic_search_enable,
            action = context.toPeopleSearch()
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

    fun menuItems(
        context: Context
    ) {
        viewModelScope.launch {
            _state.emit(
                mutableListOf<MenuItem>().apply {
                    addAll(userItems(context))
                    addAll(moviesItems(context))
                    addAll(seriesItems(context))
                    addAll(peopleItems(context))
                }.filter { it.action != null || it.type == MenuItemType.HEADER }.toList()
            )
        }

    }

}