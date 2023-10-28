package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.data.review.ReviewsResponse
import com.vlv.network.data.series.SeriesDetailResponse
import com.vlv.network.data.series.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SeriesApi {

    @GET("tv/{series_id}")
    @Mock("series/detail_GET.json")
    suspend fun seriesDetail(
        @Path("series_id") seriesId: Int
    ) : SeriesDetailResponse

    @GET("tv/{series_id}/credits")
    @Mock("series/credits_GET.json")
    suspend fun credits(
        @Path("series_id") seriesId: Int
    ) : CreditsResponse

    @GET("tv/{series_id}/reviews")
    @Mock("series/reviews_GET.json")
    suspend fun reviews(
        @Path("series_id") seriesId: Int
    ) : ReviewsResponse

    @GET("tv/{series_id}/recommendations")
    @Mock("series/recommendations_GET.json")
    suspend fun recommendations(
        @Path("series_id") seriesId: Int,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("trending/tv/{time_window}")
    @Mock("series/trending_GET.json")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/on_the_air")
    @Mock("series/on_the_air_GET.json")
    suspend fun onTheAir(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/popular")
    @Mock("series/popular_GET.json")
    suspend fun popular(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/top_rated")
    @Mock("series/top_rated_GET.json")
    suspend fun topRated(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("tv/airing_today")
    @Mock("series/airing_today_GET.json")
    suspend fun airingToday(
        @Query("page")
        page: Int
    ) : SeriesResponse

    @GET("search/tv")
    @Mock("series/search_GET.json")
    suspend fun search(
        @Query("query")
        text: String,
        @Query("page")
        page: Int
    ) : SeriesResponse

}