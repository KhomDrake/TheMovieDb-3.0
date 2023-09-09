package com.vlv.network.api

import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("trending/tv/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/on_the_air")
    suspend fun onTheAir(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/popular")
    suspend fun popular(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/top_rated")
    suspend fun topRated(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

}