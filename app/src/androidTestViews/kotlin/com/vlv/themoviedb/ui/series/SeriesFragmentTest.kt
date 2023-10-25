package com.vlv.themoviedb.ui.series

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.MovieInitializer
import com.vlv.network.NetworkInitializer
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.repository.SeriesRepository
import com.vlv.series.SeriesInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import com.vlv.themoviedb.ui.movie.movieFragment
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<SeriesRepository>(relaxed = true) }
    single { mockk<TheMovieDbDao>(relaxed = true) }
}

class SeriesFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun checkIfSeriesAllBeingLoaded() {
        seriesFragment {
            withFavorites()
            withSeriesTrending()
            withSeriesAiringToday()
        } check {
            Thread.sleep(200)
            favoritesLoaded()
            trendingLoaded()
            airingTodayLoaded()
        }
    }

}