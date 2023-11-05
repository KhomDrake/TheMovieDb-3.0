package com.vlv.series.ui.search

import androidx.lifecycle.ViewModel
import com.vlv.common.ui.search.BaseSearchViewModel
import com.vlv.data.network.database.data.HistoryType
import com.vlv.data.network.repository.SearchRepository

class SearchViewModel(
    repository: SearchRepository
) : BaseSearchViewModel(repository) {

    override val historyType: HistoryType
        get() = HistoryType.SERIES

}