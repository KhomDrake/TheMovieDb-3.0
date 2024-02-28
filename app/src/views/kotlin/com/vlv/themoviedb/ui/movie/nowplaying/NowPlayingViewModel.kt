package com.vlv.themoviedb.ui.movie.nowplaying

import androidx.lifecycle.ViewModel
import com.vlv.common.data.movie.Movie
import com.vlv.movie.data.repository.MovieRepository

class NowPlayingViewModel(private val repository: MovieRepository) : ViewModel() {

    fun nowPlaying() = repository.nowPlaying()
        .responseLiveData
        .map { it.movies.map(::Movie) }

}