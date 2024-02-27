package com.vlv.search.data.repository

import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ItemType

class HistoryRepository(
    private val dao: TheMovieDbDao
) {

    fun history(type: ItemType) = dao.historyByType(type)

    suspend fun historyAsync(type: ItemType) = dao.historyByTypeAsync(type)

    suspend fun addHistory(history: History) = dao.insertHistory(history)

    suspend fun deleteHistory(history: History) = dao.deleteHistory(history)

}