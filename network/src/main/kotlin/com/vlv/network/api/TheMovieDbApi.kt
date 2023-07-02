package com.vlv.network.api

import com.vlv.network.data.movie.MoviesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbApi {

    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("trending/movie/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("search/movie")
    suspend fun search(
        @Query("language")
        language: String,
        @Query("query")
        text: String,
        @Query("page")
        page: Int,
        @Query("include_adult")
        includeAdult: Boolean = false
    ) : MoviesResponse

}