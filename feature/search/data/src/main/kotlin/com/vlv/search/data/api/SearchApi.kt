package com.vlv.search.data.api

import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.data.common.model.people.PeoplesResponse
import com.vlv.data.common.model.tvshow.TvShowsResponse
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
    suspend fun searchTvShows(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1,
        @Query("include_adult")
    includeAdult: Boolean = false
    ) : TvShowsResponse

    @GET("search/person")
    suspend fun searchPeople(
        @Query("query")
        query: String,
        @Query("page")
        page: Int = 1
    ) : PeoplesResponse

}