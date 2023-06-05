package com.vlv.network.api

import com.vlv.network.data.movie.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : MoviesResponse

}