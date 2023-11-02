package com.vlv.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.network.api.DiscoverApi
import com.vlv.network.api.GenresApi
import com.vlv.network.api.MovieApi
import com.vlv.network.paging.MoviePagingSource
import com.vlv.network.paging.SeriesPagingSource

class GenreRepository(
    private val api: GenresApi,
    private val discoverApi: DiscoverApi
) {

    suspend fun moviesGenre() = api.moviesGenres()

    suspend fun seriesGenre() = api.seriesGenres()

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

    fun seriesByGenre(
        config: PagingConfig,
        genreId: Int
    ) = Pager(
        config = config,
        pagingSourceFactory = {
            SeriesPagingSource { page ->
                discoverApi.discoverSeries(genreId, page)
            }
        }
    ).flow

}