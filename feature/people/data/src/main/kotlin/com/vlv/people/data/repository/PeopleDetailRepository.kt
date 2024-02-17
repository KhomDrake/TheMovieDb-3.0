package com.vlv.people.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.movie.MovieResponse
import com.vlv.data.common.model.movie.MoviesResponse
import com.vlv.data.common.model.people.PeopleDetailResponse
import com.vlv.data.common.model.people.PeopleMovieCreditResponse
import com.vlv.data.common.model.people.PeopleSeriesCreditResponse
import com.vlv.data.common.paging.MoviePagingSource
import com.vlv.people.data.api.PeopleApi

class PeopleDetailRepository(private val api: PeopleApi) {

    fun peopleDetail(peopleId: Int) = bondsmith<PeopleDetailResponse>()
        .config {
            request {
                api.peopleDetail(peopleId)
            }
            withCache(false)
        }
        .execute()

    fun movieCredit(peopleId: Int) = bondsmith<PeopleMovieCreditResponse>()
        .config {
            request {
                api.peopleMovieCredits(peopleId)
            }
        }
        .execute()

    fun seriesCredit(peopleId: Int) = bondsmith<PeopleSeriesCreditResponse>()
        .config {
            request {
                api.peopleTvCredits(peopleId)
            }
        }
        .execute()

}