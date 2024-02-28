package com.vlv.data.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ImageEntity(
    val data: String,
    val type: ImageType,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)

enum class ImageType{
    BASE_URL,
    SECURE_BASE_URL,
    BACKDROP,
    LOGO,
    POSTER,
    PROFILE
}
