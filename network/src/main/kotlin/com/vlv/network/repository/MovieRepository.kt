package com.vlv.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.network.api.MovieApi
import com.vlv.network.data.movie.MovieResponse
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.paging.MoviePagingSource
import kotlinx.coroutines.flow.Flow

enum class TimeWindow {
    DAY,
    WEEK
}


class MovieRepository(private val api: MovieApi) {

    suspend fun trendingMovies(timeWindow: TimeWindow) : MoviesResponse {
        return api.trending(
            timeWindow.name.lowercase(),
            "en",
            1
        )
    }

    fun trendingMoviesPaging(
        config: PagingConfig,
        timeWindow: TimeWindow
    ) : Flow<PagingData<MovieResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    api.trending(timeWindow.name.lowercase(), "en", page)
                }
            }
        ).flow
    }

    fun search(
        config: PagingConfig,
        query: String
    ) : Flow<PagingData<MovieResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    api.search( "en", query, page)
                }
            }
        ).flow
    }

    suspend fun nowPlaying() : MoviesResponse {
        return api.nowPlaying(
            "en",
            1
        )
    }

}