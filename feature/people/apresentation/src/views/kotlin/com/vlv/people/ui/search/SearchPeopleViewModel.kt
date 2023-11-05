package com.vlv.people.ui.search

import com.vlv.common.ui.search.BaseSearchViewModel
import com.vlv.data.network.database.data.HistoryType
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import com.vlv.search.domain.usecase.SearchPeopleUseCase
import com.vlv.search.domain.usecase.SearchSeriesUseCase

class SearchPeopleViewModel(
    movieUseCase: SearchMovieUseCase,
    seriesUseCase: SearchSeriesUseCase,
    peopleUseCase: SearchPeopleUseCase,
    historyUseCase: HistoryUseCase
) : BaseSearchViewModel(movieUseCase, seriesUseCase, peopleUseCase, historyUseCase) {

    override val historyType: HistoryType
        get() = HistoryType.PEOPLE

}