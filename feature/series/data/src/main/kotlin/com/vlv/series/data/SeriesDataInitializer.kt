package com.vlv.series.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.series.data.api.SeriesApi
import com.vlv.series.data.repository.SeriesDetailRepository
import com.vlv.series.data.repository.SeriesRepository
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class SeriesDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { RetrofitFactory.service(get(), SeriesApi::class) as SeriesApi }
                single { SeriesDetailRepository(get()) }
                single { SeriesRepository(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}