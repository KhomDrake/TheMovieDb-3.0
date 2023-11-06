package com.vlv.common.ui.route

import android.content.Context

fun Context.toMovieGenre() = intentForAction("MOVIE_GENRE")

fun Context.toSeriesGenre() = intentForAction("SERIES_GENRE")
