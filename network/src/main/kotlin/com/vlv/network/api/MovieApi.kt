package com.vlv.network.api

import com.gustafah.android.mockinterceptor.Mock
import com.vlv.network.data.credit.CreditsResponse
import com.vlv.network.data.movie.MovieDetailResponse
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.data.review.ReviewsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/{movie_id}")
    @Mock("movie/movie_detail_GET.json")
    suspend fun movieDetail(
        @Path("movie_id") movieId: Int
    ) : MovieDetailResponse

    @GET("movie/{movie_id}/credits")
    @Mock("movie/movie_credits_GET.json")
    suspend fun movieCast(
        @Path("movie_id") movieId: Int
    ) : CreditsResponse

    @GET("movie/{movie_id}/reviews")
    @Mock("movie/movie_reviews_GET.json")
    suspend fun movieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page")
        page: Int
    ) : ReviewsResponse

    @GET("movie/{movie_id}/recommendations")
    @Mock("movie/movie_recommendations_GET.json")
    suspend fun movieRecommendations(
        @Path("movie_id") movieId: Int,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/now_playing")
    @Mock("movie/now_playing_GET.json")
    suspend fun nowPlaying(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/popular")
    @Mock("movie/popular_GET.json")
    suspend fun popular(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/top_rated")
    @Mock("movie/top_rated_GET.json")
    suspend fun topRated(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("movie/upcoming")
    @Mock("movie/upcoming_GET.json")
    suspend fun upcoming(
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("trending/movie/{time_window}")
    @Mock("movie/trending_GET.json")
    suspend fun trending(
        @Path("time_window") timeWindow: String,
        @Query("page")
        page: Int
    ) : MoviesResponse

    @GET("search/movie")
    @Mock("movie/search_movie_GET.json")
    suspend fun search(
        @Query("query")
        text: String,
        @Query("page")
        page: Int,
        @Query("include_adult")
        includeAdult: Boolean = false
    ) : MoviesResponse

}