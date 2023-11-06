package com.vlv.genre.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.genre.data.GenreRepository

class SeriesGenreUseCase(
    private val repository: GenreRepository
) {

    suspend fun genres() = repository.seriesGenre()

    fun seriesByGenre(
        config: PagingConfig,
        genreId: Int
    ) = repository.seriesByGenre(config, genreId)

}