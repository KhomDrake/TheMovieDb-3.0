package com.vlv.series.data

import android.content.res.Resources
import com.vlv.common.ui.adapter.Information
import com.vlv.common.ui.adapter.PillItem
import com.vlv.extensions.PATTERN_MONTH_AND_YEAR
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toHoursAndMinutes
import com.vlv.network.data.series.LastEpisodeToAir
import com.vlv.network.data.series.NextEpisodeToAir
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.series.R
import com.vlv.series.ui.detail.about.AboutItem

class SeriesDetail(
    val score: String,
    val dateAndTime: String,
    val aboutItems: List<AboutItem>
) {
    constructor(resources: Resources, response: SeriesDetailResponse) : this(
        score = String.format("%.1f", response.voteAverage),
        dateAndTime = response.run {
            val episodeRunTime = response.episodeRunTime.firstOrNull()
            episodeRunTime?.let {
                "${response.firstAirDate.toFormattedString(PATTERN_MONTH_AND_YEAR)} - ${episodeRunTime.toHoursAndMinutes(resources)}"
            }?: response.firstAirDate.toFormattedString(PATTERN_MONTH_AND_YEAR)
        },
        aboutItems = response.run {
            val items = mutableListOf<AboutItem>()
            items.add(AboutItem.BigText(
                resources.getString(R.string.series_description, overview),
                response.overview
            ))
            items.add(AboutItem.Title(R.string.series_title_genres))
            items.add(AboutItem.Genres(response.genres.map { PillItem(it.id, it.name) }))
            response.lastEpisodeToAir?.let { episode ->
                items.add(AboutItem.Title(R.string.series_title_last_episode))
                items.add(AboutItem.EpisodeItem(episode.toEpisode(resources)))
            }
            response.nextEpisodeToAir?.let { episode ->
                items.add(AboutItem.Title(R.string.series_title_next_episode))
                items.add(AboutItem.EpisodeItem(episode.toEpisode(resources)))
            }

            items.add(AboutItem.Line())
            items.add(AboutItem.Title(R.string.series_title_information))

            items.addAll(
                listOf(
                    Information(
                        title = R.string.series_information_original_title,
                        data = response.originalName
                    ),
                    Information(
                        title = R.string.series_information_first_on_air,
                        data = response.firstAirDate.toFormattedString(patternDate2())
                    ),
                    Information(
                        title = R.string.series_information_status,
                        data = response.status
                    ),
                    Information(
                        title = R.string.series_information_last_on_air,
                        data = response.lastAirDate.toFormattedString(patternDate2())
                    ),
                    Information(
                        title = R.string.series_information_aired_seasons,
                        data = response.numberOfSeasons.toString()
                    ),
                    Information(
                        title = R.string.series_information_aired_episodes,
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
                ).map { AboutItem.InformationItem(it) }
            )

            items
        }
    )
}

class Episode(
    val episodeNumberAndDate: String,
    val id: Int,
    val name: String,
    val stillPath: String?,
)

fun NextEpisodeToAir.toEpisode(resources: Resources) = Episode(
    "S${seasonNumber}E$episodeNumber - ${airDate.toFormattedString(patternDate2())}",
    id,
    name,
    stillPath
)



fun LastEpisodeToAir.toEpisode(resources: Resources) = Episode(
    "S${seasonNumber}E$episodeNumber - ${airDate.toFormattedString(patternDate2())}",
    id,
    name,
    stillPath
)