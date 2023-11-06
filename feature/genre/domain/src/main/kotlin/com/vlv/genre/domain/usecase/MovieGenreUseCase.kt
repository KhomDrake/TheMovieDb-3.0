package com.vlv.genre.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.genre.data.GenreRepository

class MovieGenreUseCase(
    private val repository: GenreRepository
) {

    suspend fun genres() = repository.moviesGenre()

    fun moviesByGenre(
        config: PagingConfig,
        genreId: Int
    ) = repository.movieByGenre(config, genreId)

}