package com.vlv.search.data

import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import com.vlv.search.R
import com.vlv.ui.R as RCommon

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
        R.string.search_tv_show_hint_text
    ),
    PERSON(
        RCommon.layout.common_people_listing_loading,
        R.string.search_people_hint_text
    )
}