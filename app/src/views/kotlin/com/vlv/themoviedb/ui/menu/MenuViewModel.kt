package com.vlv.themoviedb.ui.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.ui.route.toMovieNowPlaying
import com.vlv.common.ui.route.toMoviePopular
import com.vlv.common.ui.route.toMovieSearch
import com.vlv.common.ui.route.toMovieTopRated
import com.vlv.common.ui.route.toMovieTrending
import com.vlv.common.ui.route.toMovieUpcoming
import com.vlv.common.ui.route.toPeoplePopular
import com.vlv.common.ui.route.toPeopleSearch
import com.vlv.common.ui.route.toPeopleTrending
import com.vlv.common.ui.route.toSeriesAiringToday
import com.vlv.common.ui.route.toSeriesOnTheAir
import com.vlv.common.ui.route.toSeriesPopular
import com.vlv.common.ui.route.toSeriesTopRated
import com.vlv.common.ui.route.toSeriesTrendingNow
import com.vlv.themoviedb.R
import com.vlv.imperiya.R as RCommon

class MenuViewModel : ViewModel() {

    private fun userItems(context: Context) = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_user,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_user_option_favorite,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_review,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_session,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv_off
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
            icon = RCommon.drawable.ic_hearts,
            action = context.toMovieTrending()
        ),
        MenuItem(
            R.string.menu_title_movie_option_top_rated,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts,
            action = context.toMovieTopRated()
        ),
        MenuItem(
            R.string.menu_title_movie_option_now_playing,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts,
            action = context.toMovieNowPlaying()
        ),
        MenuItem(
            R.string.menu_title_movie_option_upcoming,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts,
            action = context.toMovieUpcoming()
        ),
        MenuItem(
            R.string.menu_title_movie_option_popular,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts,
            action = context.toMoviePopular()
        ),
        MenuItem(
            R.string.menu_title_movie_option_genres,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts
        ),
        MenuItem(
            R.string.menu_title_movie_option_discover,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts,
            action = context.toMovieSearch()
        ),
        MenuItem(
            R.string.menu_title_movie_option_updated_recent,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts
        ),
        MenuItem(
            R.string.menu_title_movie_option_certifications,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_hearts
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
            icon = RCommon.drawable.ic_tv,
            action = context.toSeriesTrendingNow()
        ),
        MenuItem(
            R.string.menu_title_series_option_top_rated,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv,
            action = context.toSeriesTopRated()
        ),
        MenuItem(
            R.string.menu_title_series_option_airing_today,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv,
            action = context.toSeriesAiringToday()
        ),
        MenuItem(
            R.string.menu_title_series_option_on_the_air,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv,
            action = context.toSeriesOnTheAir()
        ),
        MenuItem(
            R.string.menu_title_series_option_popular,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv,
            action = context.toSeriesPopular()
        ),
        MenuItem(
            R.string.menu_title_series_option_genres,
            type = MenuItemType.ITEM
        ),
        MenuItem(
            R.string.menu_title_series_option_discover,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_search,
            action = context.toPeopleSearch()
        ),
        MenuItem(
            R.string.menu_title_series_option_updated_recent,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv
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
            icon = RCommon.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_popular,
            type = MenuItemType.ITEM,
            action = context.toPeoplePopular(),
            icon = RCommon.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_trending,
            type = MenuItemType.ITEM,
            action = context.toPeopleTrending(),
            icon = RCommon.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_search,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_search,
            action = context.toPeopleSearch()
        ),
        MenuItem(
            R.string.menu_title_people_option_movie_credit,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_movie
        ),
        MenuItem(
            R.string.menu_title_people_option_tv_credit,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_tv
        ),
        MenuItem(
            R.string.menu_title_people_option_updated_recent,
            type = MenuItemType.ITEM,
            icon = RCommon.drawable.ic_people
        )
    )

    fun menuItems(
        context: Context
    ) = bondsmith<List<MenuItem>>()
        .request {
            mutableListOf<MenuItem>().apply {
                addAll(userItems(context))
                addAll(moviesItems(context))
                addAll(seriesItems(context))
                addAll(peopleItems(context))
            }.filter { it.action != null || it.type == MenuItemType.HEADER }
        }
        .execute()
        .responseLiveData

}