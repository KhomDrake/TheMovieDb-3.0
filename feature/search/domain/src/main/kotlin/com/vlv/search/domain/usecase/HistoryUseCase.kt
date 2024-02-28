package com.vlv.search.domain.usecase

import com.vlv.bondsmith.bondsmith
import com.vlv.data.database.data.History
import com.vlv.data.database.data.ItemType
import com.vlv.search.data.repository.HistoryRepository

class HistoryUseCase(
    private val repository: HistoryRepository
) {
    fun history(historyType: ItemType) = repository.history(historyType)

    fun historyAsync(type: ItemType) = run {
        bondsmith<List<History>>()
            .config {
                withCache(with = false)
                request {
                    repository.historyAsync(type)
                }
            }
            .execute()
            .responseStateFlow
    }

    fun addHistoryAsync(history: History) = bondsmith<List<History>>()
        .config {
            withCache(with = false)
            request {
                repository.addHistory(history)
                repository.historyAsync(history.type)
            }
        }
        .execute()
        .responseStateFlow

    fun deleteHistoryAsync(history: History) = bondsmith<List<History>>()
        .config {
            withCache(with = false)
            request {
                repository.deleteHistory(history)
                repository.historyAsync(history.type)
            }
        }
        .execute()
        .responseStateFlow

    suspend fun addHistory(history: History) = repository.addHistory(history)

    suspend fun deleteHistory(history: History) = repository.deleteHistory(history)
}