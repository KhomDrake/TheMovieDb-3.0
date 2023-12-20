package com.vlv.common.route

import android.content.Context
import com.vlv.extensions.intentForAction

fun Context.toMovieGenre() = intentForAction("MOVIE_GENRE")

fun Context.toSeriesGenre() = intentForAction("SERIES_GENRE")
