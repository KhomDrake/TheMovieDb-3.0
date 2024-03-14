package com.vlv.genre.presentation.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.vlv.common.data.tv_show.TvShow
import com.vlv.genre.domain.usecase.TvShowGenreUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TvShowsByGenreViewModel(
    private val useCase: TvShowGenreUseCase
) : ViewModel() {

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 5,
        maxSize = 60,
        initialLoadSize = 20
    )

    private val _tvShowByGenreState: MutableStateFlow<PagingData<TvShow>> =
        MutableStateFlow(PagingData.empty())

    val tvShowByGenreState: Flow<PagingData<TvShow>>
        get() = _tvShowByGenreState

    fun tvShowsByGenre(genreId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.tvShowsByGenre(
                pagingConfig,
                genreId
            )
                .map {
                    it.map(::TvShow)
                }
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest {
                    _tvShowByGenreState.emit(it)
                }
        }
    }

}