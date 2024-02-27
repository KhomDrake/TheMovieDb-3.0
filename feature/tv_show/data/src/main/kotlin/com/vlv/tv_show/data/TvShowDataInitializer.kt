package com.vlv.tv_show.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.tv_show.data.api.TvShowApi
import com.vlv.tv_show.data.repository.TvShowDetailRepository
import com.vlv.tv_show.data.repository.TvShowRepository
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class TvShowDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { RetrofitFactory.service(get(), TvShowApi::class) as TvShowApi }
                single { TvShowDetailRepository(get()) }
                single { TvShowRepository(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}