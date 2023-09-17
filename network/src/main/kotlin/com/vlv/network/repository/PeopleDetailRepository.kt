package com.vlv.network.repository

import com.vlv.network.api.PeopleApi

class PeopleDetailRepository(private val api: PeopleApi) {

    suspend fun peopleDetail(peopleId: Int) = api.peopleDetail(peopleId)

}