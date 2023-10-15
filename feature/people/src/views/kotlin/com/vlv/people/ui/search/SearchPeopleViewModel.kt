package com.vlv.people.ui.search

import com.vlv.common.ui.search.BaseSearchViewModel
import com.vlv.network.database.data.HistoryType
import com.vlv.network.repository.SearchRepository

class SearchPeopleViewModel(
    repository: SearchRepository
) : BaseSearchViewModel(repository) {

    override val historyType: HistoryType
        get() = HistoryType.PEOPLE

}