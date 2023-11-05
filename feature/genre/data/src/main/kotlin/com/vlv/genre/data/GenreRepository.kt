package com.vlv.genre.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.data.network.paging.MoviePagingSource
import com.vlv.data.network.paging.SeriesPagingSource
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi

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