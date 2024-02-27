package com.vlv.genre.presentation.ui.tv_show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.tv_show.TvShow
import com.vlv.genre.domain.usecase.TvShowGenreUseCase
import com.vlv.genre.presentation.data.Genre
import kotlinx.coroutines.flow.map

class TvShowGenreViewModel(
    private val genreUseCase: TvShowGenreUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    fun genres() = genreUseCase
        .genres()
        .responseLiveData
        .map {
            it.map(::Genre)
        }

    fun seriesByGenre(genreId: Int) = genreUseCase.tvShowsByGenre(pagingConfig, genreId)
        .map {
            it.map { seriesResponse ->
                TvShow(seriesResponse)
            }
        }
        .cachedIn(viewModelScope)
}
