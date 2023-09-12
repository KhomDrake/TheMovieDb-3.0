package com.vlv.series.data

import android.content.res.Resources
import com.vlv.common.ui.adapter.Information
import com.vlv.common.ui.adapter.PillItem
import com.vlv.extensions.PATTERN_MONTH_AND_YEAR
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toHoursAndMinutes
import com.vlv.extensions.toLocalDate
import com.vlv.network.data.series.LastEpisodeToAir
import com.vlv.network.data.series.NextEpisodeToAir
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.series.R

class SeriesDetail(
    val content: String,
    val genres: List<PillItem>,
    val information: List<Information>,
    val score: String,
    val dateAndTime: String,
    val nextEpisode: Episode? = null,
    val lastEpisode: Episode? = null
) {

    constructor(resources: Resources, response: SeriesDetailResponse) : this(
        response.overview,
        genres = response.genres.map { PillItem(it.id, it.name) },
        information = listOf(
            Information(
                title = R.string.series_information_original_title,
                data = response.originalName
            ),
            Information(
                title = R.string.series_information_first_on_air,
                data = response.firstAirDate.toLocalDate().toFormattedString(patternDate2())
            ),
            Information(
                title = R.string.series_information_last_on_air,
                data = response.lastAirDate.toLocalDate().toFormattedString(patternDate2())
            ),
            Information(
                title = R.string.series_information_aired_episded,
                data = response.numberOfEpisodes.toString()
            ),
            Information(
                title = R.string.series_information_language_of_origin,
                data = response.originalLanguage
            ),
            Information(
                title = R.string.series_information_duration,
                data = response.episodeRunTime.toHoursAndMinutes(resources)
            ),
            Information(
                title = R.string.series_information_country,
                data = response.originCountry.joinToString { it }
            ),
            Information(
                title = R.string.series_information_companies,
                data = response.productionCompanies.map { it.name }.joinToString { it }
            ),
        ),
        score = String.format("%.1f", response.voteAverage),
        dateAndTime = "${response.firstAirDate.toLocalDate().toFormattedString(PATTERN_MONTH_AND_YEAR)} - ${response.episodeRunTime.first().toHoursAndMinutes(resources)}",
        nextEpisode = response.nextEpisodeToAir?.toEpisode(),
        lastEpisode = response.lastEpisodeToAir?.toEpisode()
    )

}

class Episode(
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val productionCode: String,
    val runtime: Int?,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String?,
    val voteAverage: Double,
    val voteCount: Int
)

fun NextEpisodeToAir.toEpisode() = Episode(
    airDate,
    episodeNumber,
    episodeType,
    id,
    name,
    overview,
    productionCode,
    runtime,
    seasonNumber,
    showId,
    stillPath,
    voteAverage,
    voteCount
)



fun LastEpisodeToAir.toEpisode() = Episode(
    airDate,
    episodeNumber,
    episodeType,
    id,
    name,
    overview,
    productionCode,
    runtime,
    seasonNumber,
    showId,
    stillPath,
    voteAverage,
    voteCount
)