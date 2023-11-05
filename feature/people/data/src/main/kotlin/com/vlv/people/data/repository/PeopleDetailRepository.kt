package com.vlv.people.data.repository

import com.vlv.people.data.api.PeopleApi

class PeopleDetailRepository(private val api: PeopleApi) {

    suspend fun peopleDetail(peopleId: Int) = api.peopleDetail(peopleId)

    suspend fun movieCredit(peopleId: Int) = api.peopleMovieCredits(peopleId)

    suspend fun seriesCredit(peopleId: Int) = api.peopleTvCredits(peopleId)

}