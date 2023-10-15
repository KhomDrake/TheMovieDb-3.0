package com.vlv.network

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.startup.Initializer
import com.vlv.network.api.ConfigurationApi
import com.vlv.network.api.DiscoverApi
import com.vlv.network.api.GenresApi
import com.vlv.network.api.MovieApi
import com.vlv.network.api.PeopleApi
import com.vlv.network.api.SearchApi
import com.vlv.network.api.SeriesApi
import com.vlv.network.client.OkHttpFactory
import com.vlv.network.database.TheMovieDatabase
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.interceptors.InterceptorFactory
import com.vlv.network.moshi.MoshiFactory
import com.vlv.network.repository.ConfigurationRepository
import com.vlv.network.repository.FavoriteRepository
import com.vlv.network.repository.MovieDetailRepository
import com.vlv.network.repository.MovieRepository
import com.vlv.network.repository.PeopleDetailRepository
import com.vlv.network.repository.PeopleRepository
import com.vlv.network.repository.SearchRepository
import com.vlv.network.repository.SeriesDetailRepository
import com.vlv.network.repository.SeriesRepository
import com.vlv.network.retrofit.RetrofitFactory
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class NetworkInitializer : Initializer<Module> {

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
        single { RetrofitFactory.service(get(), ConfigurationApi::class) as ConfigurationApi }
        single { RetrofitFactory.service(get(), DiscoverApi::class) as DiscoverApi }
        single { RetrofitFactory.service(get(), GenresApi::class) as GenresApi }
        single { RetrofitFactory.service(get(), PeopleApi::class) as PeopleApi }
        single { RetrofitFactory.service(get(), SearchApi::class) as SearchApi }
    }

    private fun roomModule(context: Context) = module {
        single {
            Room.databaseBuilder(
                context,
                TheMovieDatabase::class.java,
                "Database TheMovieDb"
            ).build().dao()
        }
    }

    private fun repositoryModule() = module {
        single { SearchRepository(get(), get(), get(), get()) }
        single { MovieRepository(get()) }
        single { SeriesRepository(get()) }
        single { MovieDetailRepository(get()) }
        single { SeriesDetailRepository(get()) }
        single { PeopleRepository(get()) }
        single { PeopleDetailRepository(get()) }
        single { FavoriteRepository(get()) }
        single { ConfigurationRepository(androidApplication().resources, get(), get()) }
    }

    override fun create(context: Context): Module {
        val modules = listOf(
            moshiModule(),
            interceptorModule(),
            okHttpModule(),
            retrofitModule(),
            roomModule(context),
            repositoryModule()
        )

        startKoin {
            androidContext(context)
            modules(modules)
        }

        return modules.first()
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}