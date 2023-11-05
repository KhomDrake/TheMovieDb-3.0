package com.vlv.search.data.api

import com.vlv.data.network.model.movie.MoviesResponse
import com.vlv.data.network.model.people.PeoplesResponse
import com.vlv.data.network.model.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : MoviesResponse

    @GET("search/tv")
    suspend fun searchSeries(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
        @Query("include_adult")
    includeAdult: Boolean = false
    ) : SeriesResponse

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

}