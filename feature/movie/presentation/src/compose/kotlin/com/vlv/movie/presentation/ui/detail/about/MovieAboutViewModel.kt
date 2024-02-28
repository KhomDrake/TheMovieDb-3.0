package com.vlv.movie.presentation.ui.detail.about

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.bondsmith.extension.mapData
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.movie.presentation.data.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieAboutViewModel(
    private val resources: Resources,
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    private val _movieDetailState = MutableResponseStateFlow<MovieDetail>(Response())

    val aboutState: ResponseStateFlow<MovieDetail>
        get() = _movieDetailState.asResponseStateFlow()


    fun movieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetailRepository.movieDetail(movieId)
                .responseStateFlow
                .mapData { data ->
                    data?.let {
                        MovieDetail(resources, it)
                    }
                }
                .mapData {
                    if(it?.items?.isEmpty() == true) null
                    else it
                }
                .collectLatest {
                    _movieDetailState.emit(it)
                }
        }
    }

}