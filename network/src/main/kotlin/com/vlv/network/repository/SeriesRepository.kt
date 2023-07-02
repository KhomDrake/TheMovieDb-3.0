package com.vlv.network.repository

import com.vlv.network.api.SeriesApi
import com.vlv.network.data.series.SeriesResponse

class SeriesRepository(private val api: SeriesApi) {

    suspend fun trendingSeries(timeWindow: TimeWindow) : SeriesResponse {
        return api.trending(
            timeWindow.name.lowercase(),
            "en",
            1
        )
    }

    suspend fun airingToday() = api.airingToday(
        "en",
        1
    )

}