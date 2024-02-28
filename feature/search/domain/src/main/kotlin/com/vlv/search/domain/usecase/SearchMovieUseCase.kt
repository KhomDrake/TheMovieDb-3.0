package com.vlv.search.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.search.data.repository.SearchRepository

class SearchMovieUseCase(
    private val repository: SearchRepository
) {
    fun searchMovie(pagingConfig: PagingConfig, query: String) = repository.searchMovie(
        pagingConfig, query
    )
}