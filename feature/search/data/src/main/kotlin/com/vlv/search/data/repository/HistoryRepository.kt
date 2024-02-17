package com.vlv.search.data.repository

import com.vlv.data.database.TheMovieDbDao
import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType

class HistoryRepository(
    private val dao: TheMovieDbDao
) {

    fun history(type: HistoryType) = dao.historyByType(type)

    suspend fun historyAsync(type: HistoryType) = dao.historyByTypeAsync(type)

    suspend fun addHistory(history: History) = dao.insertHistory(history)

    suspend fun deleteHistory(history: History) = dao.deleteHistory(history)

}