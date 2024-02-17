package com.vlv.people.data.repository

import com.vlv.bondsmith.bondsmith
import com.vlv.data.common.model.people.PeopleDetailResponse
import com.vlv.people.data.api.PeopleApi

class PeopleDetailRepository(private val api: PeopleApi) {

    fun peopleDetail2(peopleId: Int) = bondsmith<PeopleDetailResponse>()
        .config {
            request {
                api.peopleDetail(peopleId)
            }
            withCache(false)
        }
        .execute()

    suspend fun peopleDetail(peopleId: Int) = api.peopleDetail(peopleId)

    suspend fun movieCredit(peopleId: Int) = api.peopleMovieCredits(peopleId)

    suspend fun seriesCredit(peopleId: Int) = api.peopleTvCredits(peopleId)

}