package com.vlv.genre.presentation.data

import com.vlv.data.common.model.genre.GenreResponse

data class Genre(
    val id: Int,
    val name: String
) {
    constructor(response: GenreResponse) : this(
        response.id,
        response.name
    )
}