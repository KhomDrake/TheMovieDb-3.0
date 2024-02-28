package com.vlv.common.route

import android.content.Context
import com.vlv.common.data.movie.MovieListType
import com.vlv.common.ui.DetailObject
import com.vlv.data.database.data.ItemType
import com.vlv.extensions.intentForAction

const val DETAIL_OBJECT_EXTRA = "DETAIL_OBJECT_EXTRA"
const val FINISH_AFTER_TRANSITION_EXTRA = "FINISH_AFTER_TRANSITION_EXTRA"


const val SEARCH_TYPE_EXTRA = "SEARCH_TYPE_EXTRA"

fun Context.toMovieSearch() = intentForAction("MOVIE_SEARCH")
    .putExtra(SEARCH_TYPE_EXTRA, ItemType.MOVIE.name)

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

