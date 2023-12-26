package com.vlv.movie.presentation.ui.detail

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.movie.toFavorite
import com.vlv.common.data.movie.toMovie
import com.vlv.common.ui.DetailObject
import com.vlv.favorite.domain.usecase.FavoriteUseCase
import com.vlv.movie.data.repository.MovieDetailRepository
import com.vlv.movie.presentation.data.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val resources: Resources,
    private val favoriteUseCase: FavoriteUseCase,
    private val movieDetailRepository: MovieDetailRepository
) : ViewModel() {

    private val _movieDetailState = MutableResponseStateFlow<MovieDetail>(Response())

    val movieDetailState: ResponseStateFlow<MovieDetail>
        get() = _movieDetailState.asResponseStateFlow()

    val favoriteState = MutableStateFlow<Boolean>(false)

    fun isFavorite(detailObject: DetailObject) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = favoriteUseCase.getFavorite(detailObject.id) != null
            favoriteState.emit(isFavorite)
        }
    }

    fun changeFavorite(detailObject: DetailObject, remove: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val favorite = detailObject.toMovie().toFavorite()
            if(remove) {
                favoriteUseCase.removeFavorite(favorite)
            } else {
                favoriteUseCase.addFavorite(favorite)
            }

            favoriteState.emit(!remove)
        }
    }

    fun movieDetail(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            movieDetailRepository.movieDetail(movieId)
                .responseStateFlow
                .mapData { data ->
                    data?.let {
                        MovieDetail(resources, it)
                    }
                }
                .collectLatest {
                    _movieDetailState.emit(it)
                }
        }
    }

}