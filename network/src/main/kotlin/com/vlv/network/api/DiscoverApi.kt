package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverApi {

    @GET("discover/movie")
    @Mock("discover/movies_by_genres_GET.json")
    suspend fun discoverMovieByGenres(
        @Query("with_genres")
        genre: Int,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("discover/tv")
    @Mock("discover/series_by_genres_GET.json")
    suspend fun discoverSeries(
        @Query("with_genres")
        genre: Int,
        @Query("page")
        page: Int
    ) : SeriesResponse

}