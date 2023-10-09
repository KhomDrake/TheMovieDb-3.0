package com.vlv.search.data

import androidx.annotation.LayoutRes

enum class SearchType(
    @LayoutRes
    val layout: Int
) {
    MOVIE(com.vlv.common.R.layout.common_listing_movie_loading),
    SERIES(com.vlv.common.R.layout.common_listing_series_loading),
    PERSON(com.vlv.common.R.layout.common_people_listing_loading)
}