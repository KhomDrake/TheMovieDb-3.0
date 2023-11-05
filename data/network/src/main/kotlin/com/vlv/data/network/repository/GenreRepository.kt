package com.vlv.data.network.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.data.network.api.DiscoverApi
import com.vlv.data.network.api.GenresApi
import com.vlv.data.network.api.MovieApi
import com.vlv.data.network.paging.MoviePagingSource
import com.vlv.data.network.paging.SeriesPagingSource

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