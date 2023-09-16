package com.vlv.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.network.api.MovieApi
import com.vlv.network.data.movie.MovieResponse
import com.vlv.network.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow

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