package com.vlv.genre.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class GenreDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { RetrofitFactory.service(get(), DiscoverApi::class) as DiscoverApi }
                single { RetrofitFactory.service(get(), GenresApi::class) as GenresApi }
                single { GenreRepository(get(), get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}