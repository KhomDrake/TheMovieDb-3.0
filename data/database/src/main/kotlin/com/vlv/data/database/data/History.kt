package com.vlv.data.database.data

import androidx.room.Entity

@Entity(primaryKeys = ["text", "type"])
data class History(
    val text: String,
    val type: ItemType
)