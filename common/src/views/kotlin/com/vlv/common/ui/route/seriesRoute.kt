package com.vlv.common.ui.route

import android.content.Context
import android.content.Intent
import com.vlv.common.data.series.SeriesListType
import com.vlv.common.ui.DETAIL_OBJECT_EXTRA
import com.vlv.common.ui.DetailObject

fun Context.toSeriesDetail(
    detailObject: DetailObject
) = intentForAction("SERIES_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
    }

const val SERIES_LISTING_TYPE_EXTRA = "SERIES_LISTING_TYPE_EXTRA"

fun Context.toSeriesTrendingNow() = toSeriesListing(SeriesListType.TRENDING)

fun Context.toSeriesAiringToday() = toSeriesListing(SeriesListType.AIRING_TODAY)

fun Context.toSeriesOnTheAir() = toSeriesListing(SeriesListType.ON_THE_AIR)

fun Context.toSeriesPopular() = toSeriesListing(SeriesListType.POPULAR)

fun Context.toSeriesTopRated() = toSeriesListing(SeriesListType.TOP_RATED)

fun Context.toSeriesListing(
    type: SeriesListType = SeriesListType.TRENDING
) = intentForAction("SERIES_LISTING").apply {
    putExtra(SERIES_LISTING_TYPE_EXTRA, type)
}
