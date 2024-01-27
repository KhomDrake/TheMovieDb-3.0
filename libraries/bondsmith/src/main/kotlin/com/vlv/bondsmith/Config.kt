package com.vlv.bondsmith

import androidx.annotation.WorkerThread
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.responseData
import com.vlv.bondsmith.data.responseError
import com.vlv.bondsmith.data.responseLoading
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.minutes

class Config<Data>(private var id: String) {
    private var maxDuration = 5.minutes
    private var minDuration: Duration = 200.milliseconds
    private var cacheTimeout: Duration = 10.minutes
    private var withCache: Boolean = false
    private var execution: (suspend () -> Data)? = null
    private var cacheLookup: (suspend (String) -> Data?)? = null
    private var saveCache: (suspend (String, Data) -> Unit)? = null
    private var cacheExpirationDate: Long = 0L

    fun maxDuration(duration: Duration) = apply {
        maxDuration = duration
    }

    fun minDuration(duration: Duration) = apply {
        minDuration = duration
    }

    fun withCache(with: Boolean) = apply {
        withCache = with
    }

    fun cacheTimeout(duration: Duration) = apply {
        cacheTimeout = duration
    }

    fun cacheLookup(function: suspend (String) -> Data?) = apply {
        cacheLookup = function
    }

    fun saveCache(function: suspend (id: String, data: Data) -> Unit) = apply {
        saveCache = function
    }

    fun request(function: suspend () -> Data) = apply {
        execution = function
    }

    fun configId(newId: String) = apply {
        id = newId
    }

    private fun updateCacheExpirationTime() {
        cacheExpirationDate = System.currentTimeMillis() + cacheTimeout.inWholeMilliseconds
    }

    private fun cacheExpired(currentTime: Long): Boolean {
        return currentTime - cacheTimeout.inWholeMilliseconds >= cacheExpirationDate
                || cacheExpirationDate == 0L
    }

    @WorkerThread
    suspend fun execute(
        collector: FlowCollector<Response<Data>>,
        bondsmith: Bondsmith<Data>
    ) {
        withTimeout(maxDuration) {
            runCatching {
                val currentTime = System.currentTimeMillis()
                val cacheExpired = cacheExpired(currentTime)
                collector.emit(responseLoading())

                if(!withCache) {
                    bondsmith.logInfo("[Config] Request started")
                    val data = execution?.invoke()
                    bondsmith.logInfo("[Config] Request ended, data: $data")
                    collector.emit(responseData(data))
                    bondsmith.logInfo("[Config] Data emitted: $data")
                } else if(cacheExpired && withCache) {
                    bondsmith.logInfo("[Config] Request started - Cache expired or not set")
                    val data = execution?.invoke()
                    bondsmith.logInfo("[Config] Request ended, data: $data")
                    data?.let {
                        saveCache?.apply {
                            invoke(id, data)
                            updateCacheExpirationTime()
                            bondsmith.logInfo("[Config] cache saved: $data")
                        }
                        bondsmith.logInfo("[Config] Data emitted: $data")
                        collector.emit(responseData(data))
                    }
                } else {
                    bondsmith.logInfo("[Config] using cache")
                    val cache = cacheLookup?.invoke(id)
                    cache?.let {
                        bondsmith.logInfo("[Config] cache retrieved: $cache")
                        collector.emit(responseData(cache))
                    }
                }
            }.onFailure {
                bondsmith.logInfo("[Config] Emitting error")
                collector.emit(responseError(it))
                bondsmith.logInfo("[Config] Error emitted: $it")
            }
        }
    }

}