package com.vlv.themoviedb.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.common.data.movie.Movie
import com.vlv.movie.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        nowPlaying()
    }

    val state = MutableResponseStateFlow<List<Movie>>(Response())

    fun nowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.nowPlaying()
                .responseStateFlow
                .mapData {
                    it?.movies?.map(::Movie) ?: listOf()
                }
                .collectLatest {
                    state.emit(it)
                }
        }
    }

}