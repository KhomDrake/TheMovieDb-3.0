package com.vlv.search.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.search.data.repository.SearchRepository

class SearchTvShowsUseCase(
    private val repository: SearchRepository
) {
    fun searchTvShows(pagingConfig: PagingConfig, query: String) = repository.searchTvShows(
        pagingConfig, query
    )
}