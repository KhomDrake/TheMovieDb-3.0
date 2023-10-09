package com.vlv.common.ui.route

import android.content.Context
import android.content.Intent
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.ui.DETAIL_OBJECT_EXTRA
import com.vlv.common.ui.DetailObject
import com.vlv.common.ui.FINISH_AFTER_TRANSITION_EXTRA

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.toMovieSearch() = intentForAction("MOVIE_SEARCH")

fun Context.toMovieDetail(
    detailObject: DetailObject,
    finishAfterTransition: Boolean = true
) = intentForAction("MOVIE_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
        putExtra(FINISH_AFTER_TRANSITION_EXTRA, finishAfterTransition)
    }

const val MOVIES_LISTING_TYPE_EXTRA = "MOVIES_LISTING_TYPE_EXTRA"

private fun Context.toMovieListing(type: MovieListType) =
    intentForAction("MOVIE_LISTING").apply {
        putExtra(MOVIES_LISTING_TYPE_EXTRA, type)
    }

fun Context.toMovieNowPlaying() = toMovieListing(MovieListType.NOW_PLAYING)

fun Context.toMoviePopular() = toMovieListing(MovieListType.POPULAR)

fun Context.toMovieTopRated() = toMovieListing(MovieListType.TOP_RATED)

fun Context.toMovieUpcoming() = toMovieListing(MovieListType.UPCOMING)

fun Context.toMovieTrending() = toMovieListing(MovieListType.TRENDING)

