package com.vlv.movie.data

import com.vlv.movie.R
import com.vlv.movie.ui.detail.about.adapter.Information
import com.vlv.network.data.movie.MovieDetailResponse

class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val information: List<Information>
) {
    constructor(detail: MovieDetailResponse) : this(
        detail.id,
        detail.genres.map(::Genre),
        listOf(
            Information(
                title = R.string.movie_text_original_title,
                data = detail.originalTitle
            ),
            Information(
                title = R.string.movie_text_duration,
                data = detail.runtime
            ),
            Information(
                title = R.string.movie_text_original_language,
                data = detail.originalLanguage
            ),
            Information(
                title = R.string.movie_text_companies,
                data = detail.productCompanies.map { it.name }.joinToString { it }
            ),
            Information(
                title = R.string.movie_text_state,
                data = detail.productCountries.map { it.name }.joinToString { it }
            ),
            Information(
                title = R.string.movie_text_release_status,
                data = detail.releaseData
            ),
            Information(
                title = R.string.movie_text_budget,
                data = detail.budget.toString()
            ),
            Information(
                title = R.string.movie_text_revenue,
                data = detail.revenue
            ),
        )
    )
}