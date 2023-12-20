package com.vlv.common.route

import android.content.Context
import com.vlv.common.data.series.SeriesListType
import com.vlv.common.ui.DetailObject
import com.vlv.extensions.intentForAction

fun Context.toSeriesDetail(
    detailObject: DetailObject,
    finishAfterTransition: Boolean = true
) = intentForAction("SERIES_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
        putExtra(FINISH_AFTER_TRANSITION_EXTRA, finishAfterTransition)
    }

fun Context.toSeriesSearch() = intentForAction("SERIES_SEARCH")

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
