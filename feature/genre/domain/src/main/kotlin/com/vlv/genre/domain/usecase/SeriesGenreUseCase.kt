package com.vlv.genre.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.genre.data.GenreRepository

class SeriesGenreUseCase(
    private val repository: GenreRepository
) {

    suspend fun genres() = bondsmith<List<GenreResponse>>("SERIES-GENRE")
        .config {
            request {
                repository.seriesGenre().genres
            }
        }
        .execute()

    fun seriesByGenre(
        config: PagingConfig,
        genreId: Int
    ) = repository.seriesByGenre(config, genreId)

}