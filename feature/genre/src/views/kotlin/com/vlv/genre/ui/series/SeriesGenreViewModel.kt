package com.vlv.genre.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.series.Series
import com.vlv.network.data.genre.GenreResponse
import com.vlv.network.repository.GenreRepository
import kotlinx.coroutines.flow.map

class SeriesGenreViewModel(
    private val repository: GenreRepository
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun genres() = bondsmith<List<GenreResponse>>()
        .request {
            repository.seriesGenre().genres
        }
        .execute()
        .responseLiveData

    fun seriesByGenre(genreId: Int) = repository.seriesByGenre(pagingConfig, genreId)
        .map {
            it.map { seriesResponse ->
                Series(seriesResponse)
            }
        }
        .cachedIn(viewModelScope)
}
