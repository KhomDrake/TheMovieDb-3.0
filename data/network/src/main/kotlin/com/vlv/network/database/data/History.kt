package com.vlv.network.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["text", "type"])
data class History(
    val text: String,
    val type: HistoryType
)

enum class HistoryType {
    MOVIE,
    SERIES,
    PEOPLE
}