package com.vlv.movie.presentation.ui.detail.cast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.bondsmith
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.mapData
import com.vlv.common.data.cast.Cast
import com.vlv.data.common.model.credit.CreditsResponse
import com.vlv.movie.data.repository.MovieDetailRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MovieCastViewModel(
    private val repository: MovieDetailRepository
) : ViewModel() {

    private val _state: MutableStateFlow<Response<List<Cast>>> =
        MutableStateFlow(Response())

    val state: StateFlow<Response<List<Cast>>>
        get() = _state.asStateFlow()

    fun movieCast(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            bondsmith<CreditsResponse>()
                .request {
                    repository.movieCast(movieId)
                }
                .execute()
                .stateFlow
                .mapData {
                    it?.castResponse?.map(::Cast) ?: listOf()
                }
                .collectLatest {
                    _state.emit(it)
                }
        }
    }

}