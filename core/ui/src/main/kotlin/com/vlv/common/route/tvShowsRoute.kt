package com.vlv.common.route

import android.content.Context
import com.vlv.common.data.series.TvShowListType
import com.vlv.common.ui.DetailObject
import com.vlv.data.database.data.ItemType
import com.vlv.extensions.intentForAction

fun Context.toTvShowsDetail(
    detailObject: DetailObject,
    finishAfterTransition: Boolean = true
) = intentForAction("SERIES_DETAIL")
    .apply {
        putExtra(DETAIL_OBJECT_EXTRA, detailObject)
        putExtra(FINISH_AFTER_TRANSITION_EXTRA, finishAfterTransition)
    }

fun Context.toTvShowsSearch() = intentForAction("SERIES_SEARCH")
    .putExtra(SEARCH_TYPE_EXTRA, ItemType.TV_SHOW.name)

const val TV_SHOW_LISTING_TYPE_EXTRA = "TV_SHOW_LISTING_TYPE_EXTRA"

fun Context.toTvShowTrendingNow() = toTvShowListing(TvShowListType.TRENDING)

fun Context.toTvShowAiringToday() = toTvShowListing(TvShowListType.AIRING_TODAY)

fun Context.toTvShowOnTheAir() = toTvShowListing(TvShowListType.ON_THE_AIR)

fun Context.toTvShowPopular() = toTvShowListing(TvShowListType.POPULAR)

fun Context.toTvShowTopRated() = toTvShowListing(TvShowListType.TOP_RATED)

fun Context.toTvShowListing(
    type: TvShowListType = TvShowListType.TRENDING
) = intentForAction("SERIES_LISTING").apply {
    putExtra(TV_SHOW_LISTING_TYPE_EXTRA, type)
}
