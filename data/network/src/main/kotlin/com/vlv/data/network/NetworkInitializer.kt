package com.vlv.data.network

import androidx.startup.Initializer
import com.vlv.data.network.api.DiscoverApi
import com.vlv.data.network.api.GenresApi
import com.vlv.data.network.api.MovieApi
import com.vlv.data.network.api.PeopleApi
import com.vlv.data.network.api.SearchApi
import com.vlv.data.network.api.SeriesApi
import com.vlv.data.network.client.OkHttpFactory
import com.vlv.data.network.interceptors.InterceptorFactory
import com.vlv.data.network.moshi.MoshiFactory
import com.vlv.data.network.repository.FavoriteRepository
import com.vlv.data.network.repository.GenreRepository
import com.vlv.data.network.repository.MovieDetailRepository
import com.vlv.data.network.repository.MovieRepository
import com.vlv.data.network.repository.PeopleDetailRepository
import com.vlv.data.network.repository.PeopleRepository
import com.vlv.data.network.repository.SearchRepository
import com.vlv.data.network.repository.SeriesDetailRepository
import com.vlv.data.network.repository.SeriesRepository
import com.vlv.data.network.retrofit.RetrofitFactory
import com.vlv.util.ModuleInitializer
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkInitializer : ModuleInitializer() {

    override val shouldStartKoin: Boolean
        get() = true

    override val modules: List<Module>
        get() = listOf(
            moshiModule(),
            interceptorModule(),
            okHttpModule(),
            retrofitModule(),
            repositoryModule()
        )

    private fun moshiModule() = module {
        single { MoshiFactory.moshi() }
    }

    private fun interceptorModule() = module {
        single { InterceptorFactory() }
    }

    private fun okHttpModule() = module {
        single { OkHttpFactory.create(get()) }
    }

    private fun retrofitModule() = module {
        single { RetrofitFactory.retrofit(get(), get()) }
        single { RetrofitFactory.service(get(), MovieApi::class) as MovieApi }
        single { RetrofitFactory.service(get(), SeriesApi::class) as SeriesApi }
        single { RetrofitFactory.service(get(), DiscoverApi::class) as DiscoverApi }
        single { RetrofitFactory.service(get(), GenresApi::class) as GenresApi }
        single { RetrofitFactory.service(get(), PeopleApi::class) as PeopleApi }
        single { RetrofitFactory.service(get(), SearchApi::class) as SearchApi }
    }

    private fun repositoryModule() = module {
        single { SearchRepository(get(), get(), get(), get()) }
        single { MovieRepository(get()) }
        single { SeriesRepository(get()) }
        single { MovieDetailRepository(get()) }
        single { SeriesDetailRepository(get()) }
        single { PeopleRepository(get()) }
        single { PeopleDetailRepository(get()) }
        single { GenreRepository(get(), get()) }
        single { FavoriteRepository(get()) }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}