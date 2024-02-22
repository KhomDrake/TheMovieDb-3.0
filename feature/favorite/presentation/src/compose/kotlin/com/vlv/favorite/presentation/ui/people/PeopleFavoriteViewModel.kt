package com.vlv.favorite.presentation.ui.people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.people.People
import com.vlv.favorite.domain.usecase.PeopleFavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PeopleFavoriteViewModel(
    private val peopleFavoriteUseCase: PeopleFavoriteUseCase
) : ViewModel() {

    private val _peopleState: MutableResponseStateFlow<List<People>> =
        MutableResponseStateFlow()

    val peopleState: ResponseStateFlow<List<People>>
        get() = _peopleState.asResponseStateFlow()

    fun peopleFavorites() {
        viewModelScope.launch(Dispatchers.IO) {
            peopleFavoriteUseCase.favorites()
                .responseStateFlow
                .mapData {
                    it?.map(::People) ?: listOf()
                }
                .collectLatest {
                    _peopleState.emit(it)
                }
        }
    }



}