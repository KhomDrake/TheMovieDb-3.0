package com.vlv.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.data.network.paging.MoviePagingSource
import com.vlv.movie.data.api.MovieApi

class MovieDetailRepository(private val api: MovieApi) {

    suspend fun movieDetail(movieId: Int) = api.movieDetail(movieId)

    suspend fun movieCast(movieId: Int) = api.movieCast(movieId)

    suspend fun movieReviews(movieId: Int) = api.movieReviews(movieId, page = 1)

    fun movieRecommendations(
        config: PagingConfig,
        movieId: Int
    ) = Pager(
            config = config,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    api.movieRecommendations(movieId, page = page)
                }
            }
        ).flow

}