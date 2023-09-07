package com.vlv.extensions

import android.content.Context
import android.content.Intent

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.toMovieDetail() = intentForAction("MOVIE_DETAIL")

fun Context.toSeriesDetail() = intentForAction("SERIES_DETAIL")
