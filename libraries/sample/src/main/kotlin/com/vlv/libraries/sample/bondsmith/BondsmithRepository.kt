package com.vlv.libraries.sample.bondsmith

import android.content.res.Resources.NotFoundException
import com.vlv.bondsmith.Config
import com.vlv.bondsmith.bondsmith
import kotlinx.coroutines.delay
import kotlin.random.Random
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

object CacheVault {
    val cacheTest: MutableMap<String, Int> = mutableMapOf()
}

class BondsmithRepository {

    private val config = Config<Int>("").apply {
        minDuration(200.milliseconds)
        cacheLookup { id: String ->
            CacheVault.cacheTest[id]
        }
        saveCache { id: String, data: Int ->
            CacheVault.cacheTest[id] = data
        }
        cacheTimeout(10.seconds)
        configId("Test2")
    }

    fun default(
        error: Boolean = false,
        withCache: Boolean = true
    ) = bondsmith<Int>(tag = "Sample")
        .config(
            config
                .request {
                    delay(Random.nextLong(500L, 1200L))
                    if(error) {
                        throw NotFoundException()
                    }
                    Random.nextInt()
                }
                .withCache(withCache)
        )
        .execute()

    fun flow() = default()
        .responseStateFlow

    fun responseLiveData() = default().responseLiveData

}