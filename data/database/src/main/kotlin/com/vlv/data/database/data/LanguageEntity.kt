package com.vlv.data.network.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LanguageEntity(
    val isoName: String,
    val englishName: String,
    val name: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)