package com.vlv.movie.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vlv.data.common.model.TimeWindow
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.data.common.paging.MoviePagingSource
import com.vlv.movie.data.api.MovieApi
import kotlinx.coroutines.flow.Flow


class MovieRepository(private val api: MovieApi) {

    suspend fun trendingMovies(timeWindow: TimeWindow) : MoviesResponse {
        return api.trending(
            timeWindow.name.lowercase(),
            1
        )
    }

    private fun pagingDefault(
        config: PagingConfig,
        func: suspend (page: Int) -> MoviesResponse
    ): Flow<PagingData<MovieResponse>> {
        return Pager(
            config = config,
            pagingSourceFactory = {
                MoviePagingSource { page ->
                    func.invoke(page)
                }
            }
        ).flow
    }

    fun trendingMoviesPaging(
        config: PagingConfig,
        timeWindow: TimeWindow
    ) = pagingDefault(config) { page ->
        api.trending(timeWindow.name.lowercase(), page)
    }

    fun nowPlayingPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.nowPlaying(page)
    }

    fun topRatedPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.topRated(page)
    }

    fun popularPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.popular(page)
    }

    fun upcomingPaging(
        config: PagingConfig
    ) = pagingDefault(config) { page ->
        api.upcoming(page)
    }

    fun search(
        config: PagingConfig,
        query: String
    ) = pagingDefault(config) { page ->
        api.search( query, page)
    }

    suspend fun nowPlaying() : MoviesResponse {
        return api.nowPlaying(1)
    }

}