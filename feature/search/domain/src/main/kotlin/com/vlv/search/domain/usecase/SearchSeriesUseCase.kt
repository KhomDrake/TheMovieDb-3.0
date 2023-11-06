package com.vlv.search.domain.usecase

import androidx.paging.PagingConfig
import com.vlv.search.data.repository.SearchRepository

class SearchSeriesUseCase(
    private val repository: SearchRepository
) {
    fun searchSeries(pagingConfig: PagingConfig, query: String) = repository.searchSeries(
        pagingConfig, query
    )
}