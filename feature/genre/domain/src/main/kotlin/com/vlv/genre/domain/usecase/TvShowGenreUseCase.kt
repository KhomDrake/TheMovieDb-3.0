package com.vlv.genre.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.genre.GenreResponse
import com.vlv.genre.data.GenreRepository

class TvShowGenreUseCase(
    private val repository: GenreRepository
) {

    suspend fun genres() = bondsmith<List<GenreResponse>>("TV-SHOW-GENRE")
        .config {
            request {
                repository.tvShowGenre().genres
            }
        }
        .execute()

    fun tvShowsByGenre(
        config: PagingConfig,
        genreId: Int
    ) = repository.tvShowByGenre(config, genreId)

}