package com.vlv.themoviedb.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val repository: MovieRepository) : ViewModel() {

    fun liveData() : ResponseLiveData<MoviesResponse> {
        val liveData = MutableResponseLiveData<MoviesResponse>()

        viewModelScope.launch {
            liveData.postLoading()
            liveData.postData(repository.movies())
        }

        return liveData
    }

}