package com.vlv.tv_show.data.api

import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.data.common.model.tvshow.TvShowDetailResponse
import com.vlv.data.common.model.tvshow.TvShowsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TvShowApi {

    @GET("tv/{tv_show_id}")
    suspend fun tvShowDetail(
        @Path("tv_show_id") seriesId: Int
    ) : TvShowDetailResponse

    @GET("tv/{tv_show_id}/credits")
    suspend fun credits(
        @Path("tv_show_id") seriesId: Int
    ) : CreditsResponse

    @GET("tv/{tv_show_id}/reviews")
    suspend fun reviews(
        @Path("tv_show_id") seriesId: Int
    ) : ReviewsResponse

    @GET("tv/{tv_show_id}/recommendations")
    suspend fun recommendations(
        @Path("tv_show_id") seriesId: Int,
        @Query("page")
        page: Int
    ) : TvShowsResponse

    @GET("trending/tv/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("page")
        page: Int
    ) : TvShowsResponse

    @GET("tv/on_the_air")
    suspend fun onTheAir(
        @Query("page")
        page: Int
    ) : TvShowsResponse

    @GET("tv/popular")
    suspend fun popular(
        @Query("page")
        page: Int
    ) : TvShowsResponse

    @GET("tv/top_rated")
    suspend fun topRated(
        @Query("page")
        page: Int
    ) : TvShowsResponse

    @GET("tv/airing_today")
    suspend fun airingToday(
        @Query("page")
        page: Int
    ) : TvShowsResponse

}