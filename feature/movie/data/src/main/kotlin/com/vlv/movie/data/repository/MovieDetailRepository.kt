package com.vlv.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.data.common.model.movie.MovieDetailResponse
import com.vlv.data.common.model.review.ReviewsResponse
import com.vlv.data.common.paging.MoviePagingSource
import com.vlv.movie.data.api.MovieApi

class MovieDetailRepository(private val api: MovieApi) {

    fun movieDetail(movieId: Int) = bondsmith<MovieDetailResponse>(
        "MOVIE-DETAIL-$movieId"
    )
        .config {
            request {
                api.movieDetail(movieId)
            }
        }
        .execute()

    fun movieCast(movieId: Int) = bondsmith<CreditsResponse>(
        "MOVIE-CAST-$movieId"
    )
        .config {
            request {
                api.movieCast(movieId)
            }
        }
        .execute()

    fun movieReviews(movieId: Int) = bondsmith<ReviewsResponse>()
        .config {
            request {
                api.movieReviews(movieId, page = 1)
            }
        }
        .execute()

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