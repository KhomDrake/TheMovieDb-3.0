package com.vlv.network.api

import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.data.people.PeoplesResponse
import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("region")
        region: String? = null,
        @Query("year")
        year: Int,
        @Query("page")
        page: Int = 1
    ) : MoviesResponse

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("query")
        query: String,
        @Query("first_air_date_year")
        year: Int,
        @Query("include_adult")
        includeAdult: Boolean = false,
        @Query("page")
        page: Int = 1
    ) : SeriesResponse

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

}