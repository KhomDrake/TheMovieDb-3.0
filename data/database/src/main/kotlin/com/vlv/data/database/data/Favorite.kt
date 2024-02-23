package com.vlv.data.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val itemId: Int,
    val name: String,
    val poster: String?,
    val backdrop: String?,
    val overview: String,
    val type: ItemType
)