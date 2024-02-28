package com.vlv.genre.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.data.common.paging.MoviePagingSource
import com.vlv.data.common.paging.TvShowPagingSource
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi

class GenreRepository(
    private val api: GenresApi,
    private val discoverApi: DiscoverApi
) {

    suspend fun moviesGenre() = api.moviesGenres()

    suspend fun tvShowGenre() = api.tvShowGenres()

    fun movieByGenre(
        config: PagingConfig,
        genreId: Int
    ) = Pager(
        config = config,
        pagingSourceFactory = {
            MoviePagingSource { page ->
                discoverApi.discoverMovieByGenres(genreId, page)
            }
        }
    ).flow

    fun tvShowByGenre(
        config: PagingConfig,
        genreId: Int
    ) = Pager(
        config = config,
        pagingSourceFactory = {
            TvShowPagingSource { page ->
                discoverApi.discoverTvShow(genreId, page)
            }
        }
    ).flow

}