package com.vlv.search.domain.usecase

import com.vlv.data.database.data.History
import com.vlv.data.database.data.HistoryType
import com.vlv.search.data.repository.HistoryRepository

class HistoryUseCase(
    private val repository: HistoryRepository
) {
    fun history(historyType: HistoryType) = repository.history(historyType)

    suspend fun addHistory(history: History) = repository.addHistory(history)

    suspend fun deleteHistory(history: History) = repository.deleteHistory(history)
}