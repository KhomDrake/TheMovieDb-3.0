package com.vlv.common.data.people

import android.os.Parcelable
import com.vlv.data.common.model.people.PeopleResponse
import com.vlv.data.database.data.Favorite
import com.vlv.data.database.data.ItemType
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

    constructor(favorite: Favorite) : this(
        favorite.itemId,
        favorite.name,
        favorite.overview,
        favorite.poster
    )
}

fun People.toFavorite() = Favorite(
    id,
    id,
    name,
    profilePath,
    profilePath,
    department,
    ItemType.PEOPLE
)