package com.vlv.common.route

import android.content.Context
import com.vlv.common.data.movie.Movie
import com.vlv.common.data.movie.toDetailObject
import com.vlv.common.data.people.People
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.toDetailObject
import com.vlv.data.database.data.FavoriteType
import okhttp3.Route

typealias RouteNavigation = (route: ScreenRoute, data: Any?) -> Unit

enum class ScreenRoute {
    SETTINGS,
    FAVORITES_MOVIE,
    FAVORITES_SERIES,
    FAVORITES_PEOPLE,
    MOVIE_GENRE,
    SERIES_GENRE,
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
    SERIES_DETAIL,
    SERIES_SEARCH,
    SERIES_TRENDING,
    SERIES_AIRING_TODAY,
    SERIES_ON_THE_AIR,
    SERIES_POPULAR,
    SERIES_TOP_RATED
}

fun Context.handleRoute(route: ScreenRoute, data: Any?) {
    val intent = runCatching {
        when(route) {
            ScreenRoute.SETTINGS -> toSettings()
            ScreenRoute.FAVORITES_PEOPLE -> toFavorites(FavoriteType.PEOPLE)
            ScreenRoute.FAVORITES_SERIES -> toFavorites(FavoriteType.SERIES)
            ScreenRoute.FAVORITES_MOVIE -> toFavorites(FavoriteType.MOVIE)
            ScreenRoute.MOVIE_SEARCH -> toMovieSearch()
            ScreenRoute.MOVIE_DETAIL -> toMovieDetail((data as Movie).toDetailObject())
            ScreenRoute.MOVIE_NOW_PLAYING -> toMovieNowPlaying()
            ScreenRoute.MOVIE_GENRE -> toMovieGenre()
            ScreenRoute.MOVIE_POPULAR -> toMoviePopular()
            ScreenRoute.MOVIE_TOP_RATED -> toMovieTopRated()
            ScreenRoute.MOVIE_UPCOMING -> toMovieUpcoming()
            ScreenRoute.MOVIE_TRENDING -> toMovieTrending()
            ScreenRoute.SERIES_GENRE -> toSeriesGenre()
            ScreenRoute.SERIES_SEARCH -> toSeriesSearch()
            ScreenRoute.SERIES_TRENDING -> toSeriesTrendingNow()
            ScreenRoute.SERIES_AIRING_TODAY -> toSeriesAiringToday()
            ScreenRoute.SERIES_ON_THE_AIR -> toSeriesOnTheAir()
            ScreenRoute.SERIES_POPULAR -> toSeriesPopular()
            ScreenRoute.SERIES_TOP_RATED -> toSeriesTopRated()
            ScreenRoute.SERIES_DETAIL -> toSeriesDetail((data as Series).toDetailObject())
            ScreenRoute.PEOPLE_SEARCH -> toPeopleSearch()
            ScreenRoute.PEOPLE_POPULAR -> toPeoplePopular()
            ScreenRoute.PEOPLE_TRENDING -> toPeopleTrending()
            ScreenRoute.PEOPLE_DETAIL -> toPeopleDetail(data as People)
            else -> toMain()
        }
    }.getOrDefault(toMain())

    startActivity(intent)
}

