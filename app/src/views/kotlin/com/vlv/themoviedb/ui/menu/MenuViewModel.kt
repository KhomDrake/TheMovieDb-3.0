package com.vlv.themoviedb.ui.menu

import android.content.Context
import androidx.lifecycle.ViewModel
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
import com.vlv.common.route.toSettings
import com.vlv.common.route.toTvShowAiringToday
import com.vlv.common.route.toTvShowGenre
import com.vlv.common.route.toTvShowOnTheAir
import com.vlv.common.route.toTvShowPopular
import com.vlv.common.route.toTvShowTopRated
import com.vlv.common.route.toTvShowTrendingNow
import com.vlv.common.route.toTvShowsSearch
import com.vlv.themoviedb.R

class MenuViewModel : ViewModel() {

    private fun userItems(context: Context) = listOf<MenuItem>(
        MenuItem(
            R.string.menu_title_user,
            type = MenuItemType.HEADER
        ),
        MenuItem(
            R.string.menu_title_user_option_favorite,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_review,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv_off
        ),
        MenuItem(
            R.string.menu_title_user_option_session,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_settings,
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
            icon = R.drawable.ic_movie,
            action = context.toMovieTrending()
        ),
        MenuItem(
            R.string.menu_title_movie_option_top_rated,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie,
            action = context.toMovieTopRated()
        ),
        MenuItem(
            R.string.menu_title_movie_option_now_playing,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie,
            action = context.toMovieNowPlaying()
        ),
        MenuItem(
            R.string.menu_title_movie_option_upcoming,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie,
            action = context.toMovieUpcoming()
        ),
        MenuItem(
            R.string.menu_title_movie_option_popular,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie,
            action = context.toMoviePopular()
        ),
        MenuItem(
            R.string.menu_title_movie_option_genres,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie,
            action = context.toMovieGenre()
        ),
        MenuItem(
            R.string.menu_title_movie_option_discover,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_search,
            action = context.toMovieSearch()
        ),
        MenuItem(
            R.string.menu_title_movie_option_certifications,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie
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
            icon = R.drawable.ic_tv,
            action = context.toTvShowTrendingNow()
        ),
        MenuItem(
            R.string.menu_title_series_option_top_rated,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv,
            action = context.toTvShowTopRated()
        ),
        MenuItem(
            R.string.menu_title_series_option_airing_today,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv,
            action = context.toTvShowAiringToday()
        ),
        MenuItem(
            R.string.menu_title_series_option_on_the_air,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv,
            action = context.toTvShowOnTheAir()
        ),
        MenuItem(
            R.string.menu_title_series_option_popular,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv,
            action = context.toTvShowPopular()
        ),
        MenuItem(
            R.string.menu_title_series_option_genres,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv,
            action = context.toTvShowGenre()
        ),
        MenuItem(
            R.string.menu_title_series_option_discover,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_search,
            action = context.toTvShowsSearch()
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
            icon = R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_popular,
            type = MenuItemType.ITEM,
            action = context.toPeoplePopular(),
            icon = R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_trending,
            type = MenuItemType.ITEM,
            action = context.toPeopleTrending(),
            icon = R.drawable.ic_people
        ),
        MenuItem(
            R.string.menu_title_people_option_search,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_search,
            action = context.toPeopleSearch()
        ),
        MenuItem(
            R.string.menu_title_people_option_movie_credit,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_movie
        ),
        MenuItem(
            R.string.menu_title_people_option_tv_credit,
            type = MenuItemType.ITEM,
            icon = R.drawable.ic_tv
        )
    )

    fun menuItems(
        context: Context
    ) = bondsmith<List<MenuItem>>()
        .config {
            request {
                mutableListOf<MenuItem>().apply {
                    addAll(userItems(context))
                    addAll(moviesItems(context))
                    addAll(seriesItems(context))
                    addAll(peopleItems(context))
                }.filter { it.action != null || it.type == MenuItemType.HEADER }
            }
        }
        .execute()
        .responseLiveData

}