package com.vlv.movie.data

import com.vlv.network.data.genre.GenreResponse

class Genre(
    val id: Int,
    val name: String
) {
    constructor(genreResponse: GenreResponse) : this(
        genreResponse.id,
        genreResponse.name
    )
}