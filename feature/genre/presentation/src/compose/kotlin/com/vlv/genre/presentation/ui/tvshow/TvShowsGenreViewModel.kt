package com.vlv.genre.presentation.ui.tvshow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.genre.domain.usecase.TvShowGenreUseCase
import com.vlv.genre.presentation.data.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TvShowsGenreViewModel(
    private val useCase: TvShowGenreUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Genre>>()
    val state: ResponseStateFlow<List<Genre>>
        get() = _state.asResponseStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                useCase.genres()
                    .responseStateFlow
                    .mapData {
                        it?.map(::Genre)
                    }
                    .collect {
                        _state.emit(it)
                    }
            }
        }
    }

}