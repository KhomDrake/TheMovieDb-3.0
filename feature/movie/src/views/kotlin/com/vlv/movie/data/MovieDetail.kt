package com.vlv.movie.data

import android.content.res.Resources
import com.vlv.common.ui.adapter.Information
import com.vlv.common.ui.adapter.PillItem
import com.vlv.extensions.PATTERN_MONTH_AND_YEAR
import com.vlv.extensions.capitalizeCustom
import com.vlv.extensions.patternDate
import com.vlv.extensions.toBillionsAndMillions
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toHoursAndMinutes
import com.vlv.extensions.toLocalDate
import com.vlv.extensions.toMillionsAndThousands
import com.vlv.movie.R
import com.vlv.movie.ui.detail.about.AboutItem
import com.vlv.network.data.movie.MovieDetailResponse

class MovieDetail(
    val id: Int,
    val items: List<AboutItem>,
    val score: String,
    val dateAndTime: String,
) {
    constructor(resources: Resources, response: MovieDetailResponse) : this(
        response.id,
        response.run {
            val items = mutableListOf<AboutItem>()

            items.add(AboutItem.BigText(response.overview))
            items.add(AboutItem.Title(R.string.movie_title_genres))
            items.add(AboutItem.Genres(response.genres.map { PillItem(it.id, it.name) }))

            items.add(AboutItem.Line())
            items.add(AboutItem.Title(R.string.movie_title_information))

            items.addAll(
                listOf(
                    Information(
                        title = R.string.movie_text_original_title,
                        data = response.originalTitle
                    ),
                    Information(
                        title = R.string.movie_text_duration,
                        data = response.runtime.toHoursAndMinutes(resources)
                    ),
                    Information(
                        title = R.string.movie_text_original_language,
                        data = response.originalLanguage.capitalizeCustom()
                    ),
                    Information(
                        title = R.string.movie_text_companies,
                        data = response.productCompanies.map { it.name }.joinToString { it }
                    ),
                    Information(
                        title = R.string.movie_text_state,
                        data = response.productCountries.map { it.name }.joinToString { it }
                    ),
                    Information(
                        title = R.string.movie_text_release_status,
                        data = response.releaseData.toLocalDate().toFormattedString(
                            pattern = patternDate()
                        )
                    ),
                    Information(
                        title = R.string.movie_text_budget,
                        data = response.budget.toMillionsAndThousands(resources)
                    ),
                    Information(
                        title = R.string.movie_text_revenue,
                        data = response.revenue.toBillionsAndMillions(resources)
                    ),
                ).map { AboutItem.InformationItem(it) }
            )

            items
        },
        String.format("%.1f", response.voteAverage),
        "${response.releaseData.toLocalDate().toFormattedString(PATTERN_MONTH_AND_YEAR)} - ${response.runtime.toHoursAndMinutes(resources)}"
    )
}