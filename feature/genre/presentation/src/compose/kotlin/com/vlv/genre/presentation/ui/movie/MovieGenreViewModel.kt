package com.vlv.genre.presentation.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.genre.domain.usecase.MovieGenreUseCase
import com.vlv.genre.presentation.data.Genre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieGenreViewModel(
    private val genreUseCase: MovieGenreUseCase
) : ViewModel() {

    private val _state = MutableResponseStateFlow<List<Genre>>()
    val state: ResponseStateFlow<List<Genre>>
        get() = _state.asResponseStateFlow()

    fun loadGenres() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                genreUseCase.genres()
                    .responseStateFlow
                    .mapData { genres -> genres?.map(::Genre) }
                    .collectLatest {
                        _state.emit(it)
                    }
            }
        }
    }

}