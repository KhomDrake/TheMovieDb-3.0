package com.vlv.common.ui.route

import android.content.Context
import android.content.Intent
import com.vlv.common.ui.DETAIL_OBJECT_EXTRA
import com.vlv.common.ui.DetailObject

fun Context.intentForAction(name: String) =
    Intent("$packageName.$name")

fun Context.toMovieDetail(
    detailObject: DetailObject
) = intentForAction("MOVIE_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
    }

fun Context.toSeriesDetail(
    detailObject: DetailObject
) = intentForAction("SERIES_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
    }
