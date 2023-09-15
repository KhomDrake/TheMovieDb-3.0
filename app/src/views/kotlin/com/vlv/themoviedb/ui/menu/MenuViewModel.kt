package com.vlv.themoviedb.ui.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.common.ui.route.toMovieNowPlaying
import com.vlv.common.ui.route.toMoviePopular
import com.vlv.common.ui.route.toMovieTopRated
import com.vlv.common.ui.route.toMovieTrending
import com.vlv.common.ui.route.toMovieUpcoming
import com.vlv.common.ui.route.toSeriesAiringToday
import com.vlv.common.ui.route.toSeriesOnTheAir
import com.vlv.common.ui.route.toSeriesPopular
import com.vlv.common.ui.route.toSeriesTopRated
import com.vlv.common.ui.route.toSeriesTrendingNow
import com.vlv.themoviedb.R
import com.vlv.imperiya.R as RCommon

class MenuViewModel : ViewModel() {

    fun menuItems(
        context: Context
    ) = bondsmith<List<MenuItem>>()
        .request {
            listOf(
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
                ),
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
                    icon = RCommon.drawable.ic_hearts
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
                MenuItem(
                    R.string.menu_title_series,
                    type = MenuItemType.HEADER
                ),
                MenuItem(
                    R.string.menu_title_series_option_trending,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts,
                    action = context.toSeriesTrendingNow()
                ),
                MenuItem(
                    R.string.menu_title_series_option_top_rated,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts,
                    action = context.toSeriesTopRated()
                ),
                MenuItem(
                    R.string.menu_title_series_option_airing_today,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts,
                    action = context.toSeriesAiringToday()
                ),
                MenuItem(
                    R.string.menu_title_series_option_on_the_air,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts,
                    action = context.toSeriesOnTheAir()
                ),
                MenuItem(
                    R.string.menu_title_series_option_popular,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts,
                    action = context.toSeriesPopular()
                ),
                MenuItem(
                    R.string.menu_title_series_option_genres,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_series_option_discover,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_series_option_updated_recent,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_series_option_certifications,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_people,
                    type = MenuItemType.HEADER
                ),
                MenuItem(
                    R.string.menu_title_people_option_trending,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_people_option_search,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_people_option_movie_credit,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_people_option_tv_credit,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                ),
                MenuItem(
                    R.string.menu_title_people_option_updated_recent,
                    type = MenuItemType.ITEM,
                    icon = RCommon.drawable.ic_hearts
                )
            )
        }
        .execute()
        .responseLiveData

}