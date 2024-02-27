package com.vlv.common.route

import android.content.Context
import android.content.Intent
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.data.people.People
import com.vlv.common.data.series.TvShow
import com.vlv.common.data.series.toDetailObject
import com.vlv.data.database.data.ItemType

typealias RouteNavigation = (route: ScreenRoute, data: Any?) -> Unit

enum class ScreenRoute {
    SETTINGS,
    FAVORITES_MOVIE,
    FAVORITES_TV_SHOW,
    FAVORITES_PEOPLE,
    MOVIE_GENRE,
    TV_SHOW_GENRE,
    MAIN,
    MOVIE_SEARCH,
    MOVIE_DETAIL,
    MOVIE_NOW_PLAYING,
    MOVIE_POPULAR,
    MOVIE_TOP_RATED,
    MOVIE_UPCOMING,
    MOVIE_TRENDING,
    PEOPLE_POPULAR,
    PEOPLE_TRENDING,
    PEOPLE_SEARCH,
    PEOPLE_DETAIL,
    TV_SHOW_DETAIL,
    TV_SHOW_SEARCH,
    TV_SHOW_TRENDING,
    TV_SHOW_AIRING_TODAY,
    TV_SHOW_ON_THE_AIR,
    TV_SHOW_POPULAR,
    TV_SHOW_TOP_RATED,
    RESTART_THE_APP
}

fun Context.handleRoute(route: ScreenRoute, data: Any?) {
    val intent = runCatching {
        when(route) {
            ScreenRoute.SETTINGS -> toSettings()
            ScreenRoute.FAVORITES_PEOPLE -> toFavorites(ItemType.PEOPLE)
            ScreenRoute.FAVORITES_TV_SHOW -> toFavorites(ItemType.TV_SHOW)
            ScreenRoute.FAVORITES_MOVIE -> toFavorites(ItemType.MOVIE)
            ScreenRoute.MOVIE_SEARCH -> toMovieSearch()
            ScreenRoute.MOVIE_DETAIL -> toMovieDetail((data as Movie).toDetailObject())
            ScreenRoute.MOVIE_NOW_PLAYING -> toMovieNowPlaying()
            ScreenRoute.MOVIE_GENRE -> toMovieGenre()
            ScreenRoute.MOVIE_POPULAR -> toMoviePopular()
            ScreenRoute.MOVIE_TOP_RATED -> toMovieTopRated()
            ScreenRoute.MOVIE_UPCOMING -> toMovieUpcoming()
            ScreenRoute.MOVIE_TRENDING -> toMovieTrending()
            ScreenRoute.TV_SHOW_GENRE -> toSeriesGenre()
            ScreenRoute.TV_SHOW_SEARCH -> toTvShowsSearch()
            ScreenRoute.TV_SHOW_TRENDING -> toTvShowTrendingNow()
            ScreenRoute.TV_SHOW_AIRING_TODAY -> toTvShowAiringToday()
            ScreenRoute.TV_SHOW_ON_THE_AIR -> toTvShowOnTheAir()
            ScreenRoute.TV_SHOW_POPULAR -> toTvShowPopular()
            ScreenRoute.TV_SHOW_TOP_RATED -> toTvShowTopRated()
            ScreenRoute.TV_SHOW_DETAIL -> toTvShowsDetail((data as TvShow).toDetailObject())
            ScreenRoute.PEOPLE_SEARCH -> toPeopleSearch()
            ScreenRoute.PEOPLE_POPULAR -> toPeoplePopular()
            ScreenRoute.PEOPLE_TRENDING -> toPeopleTrending()
            ScreenRoute.PEOPLE_DETAIL -> toPeopleDetail(data as People)
            ScreenRoute.RESTART_THE_APP -> toMain()
                .apply {
                    addFlags(
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
                }
            else -> toMain()
        }
    }.getOrDefault(toMain())

    startActivity(intent)
}

