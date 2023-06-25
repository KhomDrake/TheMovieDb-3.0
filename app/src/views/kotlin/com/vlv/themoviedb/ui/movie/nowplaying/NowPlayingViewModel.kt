package com.vlv.themoviedb.ui.movie.nowplaying

import androidx.lifecycle.ViewModel
import com.vlv.bondsmith.bondsmith
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.repository.MovieRepository

class NowPlayingViewModel(private val repository: MovieRepository) : ViewModel() {

    fun nowPlaying() = bondsmith<MoviesResponse>()
        .request {
            repository.nowPlaying()
        }
        .execute()
        .responseLiveData

}