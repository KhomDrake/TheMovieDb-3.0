package com.vlv.network.repository

import com.vlv.network.api.TheMovieDbApi
import com.vlv.network.data.movie.MoviesResponse

enum class TimeWindow {
    DAY,
    WEEK
}


class MovieRepository(private val api: TheMovieDbApi) {

    suspend fun trendingMovies(timeWindow: TimeWindow) : MoviesResponse {
        return api.trending(
            timeWindow.name.lowercase(),
            "en",
            1
        )
    }

    suspend fun nowPlaying() : MoviesResponse {
        return api.nowPlaying(
            "en",
            1
        )
    }

}