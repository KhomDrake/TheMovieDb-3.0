package com.vlv.network.repository

import com.vlv.network.api.TheMovieDbApi
import com.vlv.network.data.movie.MoviesResponse

class MovieRepository(private val api: TheMovieDbApi) {

    suspend fun movies() : MoviesResponse {
        return api.nowPlaying(
            "en",
            2
        )
    }

}