package com.vlv.network.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class History(
    @PrimaryKey val text: String,
    val type: HistoryType
)

enum class HistoryType {
    MOVIE,
    SERIES,
    PEOPLE
}