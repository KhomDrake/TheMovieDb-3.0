package com.vlv.people.data

import androidx.startup.Initializer
import com.vlv.data.database.DatabaseInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.people.data.api.PeopleApi
import com.vlv.people.data.repository.PeopleDetailRepository
import com.vlv.people.data.repository.PeopleRepository
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class PeopleDataInitializer : ModuleInitializer() {

    override val modules: List<Module>
        get() = listOf(
            module {
                single { RetrofitFactory.service(get(), PeopleApi::class) as PeopleApi }
                single { PeopleRepository(get()) }
                single { PeopleDetailRepository(get()) }
            }
        )

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(
            NetworkInitializer::class.java,
            DatabaseInitializer::class.java
        )
    }

}