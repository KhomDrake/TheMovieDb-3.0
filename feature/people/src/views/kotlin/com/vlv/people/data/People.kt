package com.vlv.people.data

import com.vlv.network.data.people.PeopleResponse

class People(
    val id: Int,
    val name: String,
    val department: String,
    val profilePath: String? = null
) {
    constructor(peopleResponse: PeopleResponse) : this(
        peopleResponse.id,
        peopleResponse.name,
        peopleResponse.knownForDepartment,
        peopleResponse.profilePath
    )
}