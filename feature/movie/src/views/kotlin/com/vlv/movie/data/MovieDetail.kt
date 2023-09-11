package com.vlv.movie.data

import android.content.res.Resources
import com.vlv.extensions.PATTERN_MONTH_AND_YEAR
import com.vlv.extensions.capitalizeCustom
import com.vlv.extensions.patternDate
import com.vlv.extensions.toBillionsAndMillions
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toHoursAndMinutes
import com.vlv.extensions.toLocalDate
import com.vlv.extensions.toMillionsAndThousands
import com.vlv.movie.R
import com.vlv.movie.ui.detail.about.adapter.Information
import com.vlv.network.data.movie.MovieDetailResponse

class MovieDetail(
    val id: Int,
    val genres: List<Genre>,
    val information: List<Information>,
    val score: String,
    val dateAndTime: String,
) {
    constructor(resources: Resources, detail: MovieDetailResponse) : this(
        detail.id,
        detail.genres.map(::Genre),
        listOf(
            Information(
                title = R.string.movie_text_original_title,
                data = detail.originalTitle
            ),
            Information(
                title = R.string.movie_text_duration,
                data = detail.runtime.toHoursAndMinutes(resources)
            ),
            Information(
                title = R.string.movie_text_original_language,
                data = detail.originalLanguage.capitalizeCustom()
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
                data = detail.releaseData.toLocalDate().toFormattedString(
                    pattern = patternDate()
                )
            ),
            Information(
                title = R.string.movie_text_budget,
                data = detail.budget.toMillionsAndThousands(resources)
            ),
            Information(
                title = R.string.movie_text_revenue,
                data = detail.revenue.toBillionsAndMillions(resources)
            ),
        ),
        String.format("%.1f", detail.voteAverage),
        "${detail.releaseData.toLocalDate().toFormattedString(PATTERN_MONTH_AND_YEAR)} - ${detail.runtime.toHoursAndMinutes(resources)}"
    )
}