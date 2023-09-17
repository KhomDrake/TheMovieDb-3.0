package com.vlv.common.data.people

import android.os.Parcelable
import com.vlv.network.data.people.PeopleResponse
import kotlinx.parcelize.Parcelize

@Parcelize
class People(
    val id: Int,
    val name: String,
    val department: String,
    val profilePath: String? = null
) : Parcelable {
    constructor(peopleResponse: PeopleResponse) : this(
        peopleResponse.id,
        peopleResponse.name,
        peopleResponse.knownForDepartment,
        peopleResponse.profilePath
    )
}