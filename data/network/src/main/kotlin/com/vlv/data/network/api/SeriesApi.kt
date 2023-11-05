package com.vlv.data.network.api

import com.vlv.data.network.model.credit.CreditsResponse
import com.vlv.data.network.model.review.ReviewsResponse
import com.vlv.data.network.model.series.SeriesDetailResponse
import com.vlv.data.network.model.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("tv/{series_id}")
    suspend fun seriesDetail(
        @Path("series_id") seriesId: Int
    ) : SeriesDetailResponse

    @GET("tv/{series_id}/credits")
    suspend fun credits(
        @Path("series_id") seriesId: Int
    ) : CreditsResponse

    @GET("tv/{series_id}/reviews")
    suspend fun reviews(
        @Path("series_id") seriesId: Int
    ) : ReviewsResponse

    @GET("tv/{series_id}/recommendations")
    suspend fun recommendations(
        @Path("series_id") seriesId: Int,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/popular")
    suspend fun popular(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/top_rated")
    suspend fun topRated(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("search/tv")
    suspend fun search(
        @Query("query")
        text: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

}