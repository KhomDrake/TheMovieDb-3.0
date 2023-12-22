package com.vlv.themoviedb.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.common.data.movie.Movie
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.movie.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NowPlayingViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    init {
        nowPlaying()
    }

    val state = MutableStateFlow<Response<List<Movie>>>(Response())

    fun nowPlaying() {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<MoviesResponse>()
                .request {
                    repository.nowPlaying()
                }
                .execute()
                .stateFlow
                .collectLatest {
                    state.emit(
                        Response(
                            state = it.state,
                            error = it.error,
                            data = it.data?.movies?.map(::Movie) ?: listOf()
                        )
                    )
                }
        }
    }

}