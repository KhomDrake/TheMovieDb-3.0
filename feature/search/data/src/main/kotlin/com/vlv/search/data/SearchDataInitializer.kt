package com.vlv.search.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.search.data.api.SearchApi
import com.vlv.search.data.repository.HistoryRepository
import com.vlv.search.data.repository.SearchRepository
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class SearchDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { RetrofitFactory.service(get(), SearchApi::class) as SearchApi }
                single { HistoryRepository(get()) }
                single { SearchRepository(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}