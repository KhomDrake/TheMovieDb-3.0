package com.vlv.network.api

import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.data.review.ReviewsResponse
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("tv/{series_id}")
    suspend fun seriesDetail(
        @Path("series_id") seriesId: Int,
        @Query("language")
        language: String
    ) : SeriesDetailResponse

    @GET("tv/{series_id}/credits")
    suspend fun credits(
        @Path("series_id") seriesId: Int,
        @Query("language")
        language: String
    ) : CreditsResponse

    @GET("tv/{series_id}/reviews")
    suspend fun reviews(
        @Path("series_id") seriesId: Int,
        @Query("language")
        language: String
    ) : ReviewsResponse

    @GET("tv/{series_id}/recommendations")
    suspend fun recommendations(
        @Path("series_id") seriesId: Int,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/popular")
    suspend fun popular(
        @Query("language")
        language: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/top_rated")
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

    @GET("search/tv")
    suspend fun search(
        @Query("language")
        language: String,
        @Query("query")
        text: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

}