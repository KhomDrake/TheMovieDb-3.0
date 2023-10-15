package com.vlv.network.api

import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("discover/movie")
    suspend fun discoverMovieByGenres(
        @Query("with_genres")
        genre: Int,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("discover/tv")
    suspend fun discoverSeries(
        @Query("with_genres")
        genre: Int,
        @Query("page")
        page: Int
    ) : SeriesResponse

}