package com.vlv.genre.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.genre.data.GenreRepository

class MovieGenreUseCase(
    private val repository: GenreRepository
) {

    fun genres() = bondsmith<List<GenreResponse>>("MOVIES-GENRE")
        .config {
            request {
                repository.moviesGenre().genres
            }
        }
        .execute()

    fun moviesByGenre(
        config: PagingConfig,
        genreId: Int
    ) = repository.movieByGenre(config, genreId)

}