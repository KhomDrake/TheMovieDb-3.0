package com.vlv.people.ui.detail.tab

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import com.vlv.common.data.people.People
import com.vlv.people.data.repository.PeopleDetailRepository
import com.vlv.people.ui.data.PeopleDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PeopleAboutViewModel(
    private val repository: PeopleDetailRepository
) : ViewModel() {

    private val _detailData = MutableResponseStateFlow<PeopleDetail>()

    val detailState: ResponseStateFlow<PeopleDetail>
        get() = _detailData.asResponseStateFlow()

    fun detail(people: People) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.peopleDetail(people.id)
                .responseStateFlow
                .mapData {
                    it?.run {
                        PeopleDetail(this)
                    }
                }
                .collectLatest {
                    _detailData.emit(it)
                }
        }
    }

}