package com.vlv.search.data

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.vlv.common.R as RCommon
import com.vlv.search.R

enum class SearchType(
    @LayoutRes
    val layout: Int,
    @StringRes
    val hint: Int
) {
    MOVIE(
        RCommon.layout.common_listing_movie_loading,
        R.string.search_movie_hint_text
    ),
    SERIES(
        RCommon.layout.common_listing_series_loading,
        R.string.search_series_hint_text
    ),
    PERSON(
        RCommon.layout.common_people_listing_loading,
        R.string.search_people_hint_text
    )
}