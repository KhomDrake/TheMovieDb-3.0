package com.vlv.network.data.genre

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GenreResponse(
    val id: Int,
    val name: String
)