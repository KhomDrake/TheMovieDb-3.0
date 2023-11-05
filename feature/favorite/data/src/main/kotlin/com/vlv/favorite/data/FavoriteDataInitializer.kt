package com.vlv.favorite.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class FavoriteDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(module {
            single { FavoriteRepository(get()) }
        })

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}