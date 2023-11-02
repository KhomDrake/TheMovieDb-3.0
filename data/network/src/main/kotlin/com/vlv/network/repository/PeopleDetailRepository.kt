package com.vlv.network.repository

import com.vlv.network.api.PeopleApi

class PeopleDetailRepository(private val api: PeopleApi) {

    suspend fun peopleDetail(peopleId: Int) = api.peopleDetail(peopleId)

    suspend fun movieCredit(peopleId: Int) = api.peopleMovieCredits(peopleId)

    suspend fun seriesCredit(peopleId: Int) = api.peopleTvCredits(peopleId)

}