package com.vlv.data.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryEntity(
    val isoName: String,
    val englishName: String,
    val nativeName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)
