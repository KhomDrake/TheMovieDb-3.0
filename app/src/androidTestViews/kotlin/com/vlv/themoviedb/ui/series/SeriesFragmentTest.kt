package com.vlv.themoviedb.ui.series

import com.vlv.favorite.FavoriteInitializer
import com.vlv.movie.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import com.vlv.series.SeriesInitializer
import com.vlv.series.data.repository.SeriesRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<SeriesRepository>(relaxed = true) }
    single { mockk<SeriesFavoriteUseCase>(relaxed = true) }
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
    @Ignore("Not working on CI")
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