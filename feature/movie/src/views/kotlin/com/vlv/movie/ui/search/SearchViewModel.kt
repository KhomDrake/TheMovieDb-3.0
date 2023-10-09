package com.vlv.movie.ui.search

import com.vlv.common.ui.search.BaseSearchViewModel
import com.vlv.network.database.data.HistoryType
import com.vlv.network.repository.SearchRepository

class SearchViewModel(
    searchRepository: SearchRepository
) : BaseSearchViewModel(searchRepository) {

    override val historyType: HistoryType
        get() = HistoryType.MOVIE

}