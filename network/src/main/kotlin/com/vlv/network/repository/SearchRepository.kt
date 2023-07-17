package com.vlv.network.repository

import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType

class SearchRepository(private val dao: TheMovieDbDao) {

    fun history(type: HistoryType) = dao.historyByType(type)

    suspend fun addHistory(history: History) = dao.insertHistory(history)

    suspend fun deleteHistory(history: History) = dao.deleteHistory(history)

}