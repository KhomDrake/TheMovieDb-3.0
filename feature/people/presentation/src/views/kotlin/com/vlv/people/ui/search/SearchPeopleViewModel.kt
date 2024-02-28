package com.vlv.people.ui.search

import com.vlv.common.ui.search.BaseSearchViewModel
import com.vlv.data.database.data.ItemType
import com.vlv.search.domain.usecase.HistoryUseCase
import com.vlv.search.domain.usecase.SearchMovieUseCase
import com.vlv.search.domain.usecase.SearchPeopleUseCase
import com.vlv.search.domain.usecase.SearchTvShowsUseCase

class SearchPeopleViewModel(
    movieUseCase: SearchMovieUseCase,
    seriesUseCase: SearchTvShowsUseCase,
    peopleUseCase: SearchPeopleUseCase,
    historyUseCase: HistoryUseCase
) : BaseSearchViewModel(movieUseCase, seriesUseCase, peopleUseCase, historyUseCase) {

    override val historyType: ItemType
        get() = ItemType.PEOPLE

}