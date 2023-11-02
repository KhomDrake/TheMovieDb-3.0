package com.vlv.network.api

import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.data.movie.MovieDetailResponse
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.data.review.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    suspend fun movieDetail(
        @Path("movie_id") movieId: Int
    ) : MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    suspend fun movieCast(
        @Path("movie_id") movieId: Int
    ) : CreditsResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun movieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page")
        page: Int
    ) : ReviewsResponse

    @GET("movie/{movie_id}/recommendations")
    suspend fun movieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/now_playing")
    suspend fun nowPlaying(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/popular")
    suspend fun popular(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/top_rated")
    suspend fun topRated(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/upcoming")
    suspend fun upcoming(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("trending/movie/{time_window}")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("search/movie")
    suspend fun search(
        @Query("query")
        text: String,
        @Query("page")
        page: Int,
        @Query("include_adult")
        includeAdult: Boolean = false
    ) : MoviesResponse

}