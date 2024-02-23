package com.vlv.tv_show.presentation.model

import android.content.res.Resources
import com.vlv.common.data.about.AboutItem
import com.vlv.common.data.about.Information
import com.vlv.common.data.about.PillItem
import com.vlv.data.common.model.tvshow.LastEpisodeToAir
import com.vlv.data.common.model.tvshow.NextEpisodeToAir
import com.vlv.data.common.model.tvshow.TvShowDetailResponse
import com.vlv.extensions.PATTERN_MONTH_AND_YEAR
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.extensions.toHoursAndMinutes
import com.vlv.tv_show.R

class TvShowDetail(
    val score: String,
    val dateAndTime: String,
    val aboutItems: List<AboutItem>
) {
    constructor(resources: Resources, response: TvShowDetailResponse) : this(
        score = String.format("%.1f", response.voteAverage),
        dateAndTime = response.run {
            val episodeRunTime = response.episodeRunTime.firstOrNull()
            episodeRunTime?.let {
                "${response.firstAirDate.toFormattedString(PATTERN_MONTH_AND_YEAR)} - ${episodeRunTime.toHoursAndMinutes(resources)}"
            }?: response.firstAirDate.toFormattedString(PATTERN_MONTH_AND_YEAR)
        },
        aboutItems = response.run {
            val items = mutableListOf<AboutItem>()
            items.add(
                AboutItem.BigText(
                resources.getString(R.string.tv_show_description, overview),
                response.overview
            ))
            items.add(AboutItem.Title(R.string.tv_show_title_genres))
            items.add(AboutItem.Genres(response.genres.map { PillItem(it.id, it.name) }))
            response.lastEpisodeToAir?.let { episode ->
                items.add(AboutItem.Title(R.string.tv_show_title_last_episode))
                items.add(episode.toEpisode(resources))
            }
            response.nextEpisodeToAir?.let { episode ->
                items.add(AboutItem.Title(R.string.tv_show_title_next_episode))
                items.add(episode.toEpisode(resources))
            }

            items.add(AboutItem.Line())
            items.add(AboutItem.Title(R.string.tv_show_title_information))

            items.addAll(
                listOf(
                    Information(
                        title = R.string.tv_show_information_original_title,
                        data = response.originalName
                    ),
                    Information(
                        title = R.string.tv_show_information_first_on_air,
                        data = response.firstAirDate.toFormattedString(patternDate2())
                    ),
                    Information(
                        title = R.string.tv_show_information_status,
                        data = response.status
                    ),
                    Information(
                        title = R.string.tv_show_information_last_on_air,
                        data = response.lastAirDate.toFormattedString(patternDate2())
                    ),
                    Information(
                        title = R.string.tv_show_information_aired_seasons,
                        data = response.numberOfSeasons.toString()
                    ),
                    Information(
                        title = R.string.tv_show_information_aired_episodes,
                        data = response.numberOfEpisodes.toString()
                    ),
                    Information(
                        title = R.string.tv_show_information_language_of_origin,
                        data = response.originalLanguage
                    ),
                    Information(
                        title = R.string.tv_show_information_duration,
                        data = response.episodeRunTime.toHoursAndMinutes(resources)
                    ),
                    Information(
                        title = R.string.tv_show_information_country,
                        data = response.originCountry.joinToString { it }
                    ),
                    Information(
                        title = R.string.tv_show_information_companies,
                        data = response.productionCompanies.map { it.name }.joinToString { it }
                    ),
                ).map { AboutItem.InformationItem(it) }
            )

            items
        }
    )
}

fun NextEpisodeToAir.toEpisode(resources: Resources) = AboutItem.Episode(
    "S${seasonNumber}E$episodeNumber - ${airDate.toFormattedString(patternDate2())}",
    name,
    stillPath
)



fun LastEpisodeToAir.toEpisode(resources: Resources) = AboutItem.Episode(
    "S${seasonNumber}E$episodeNumber - ${airDate.toFormattedString(patternDate2())}",
    name,
    stillPath
)