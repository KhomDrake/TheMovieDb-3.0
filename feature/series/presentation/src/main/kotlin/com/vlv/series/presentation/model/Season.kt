package com.vlv.series.presentation.model

import android.content.res.Resources
import com.vlv.data.common.model.series.SeasonResponse
import com.vlv.extensions.patternDate2
import com.vlv.extensions.toFormattedString
import com.vlv.series.R

class Season(
    val id: Int,
    val title: String,
    val description: String,
    val poster: String? = null
) {
    constructor(resources: Resources, seasonResponse: SeasonResponse) : this(
        id = seasonResponse.id,
        title = seasonResponse.name,
        description = resources.run {
            seasonResponse.airDate?.let { date ->
                getString(
                    R.string.series_season_description,
                    seasonResponse.episodeCount,
                    date.toFormattedString(patternDate2())
                )
            } ?: getString(
                R.string.series_season_description_without_date,
                seasonResponse.episodeCount
            )
        },
        poster = seasonResponse.posterPath
    )
}