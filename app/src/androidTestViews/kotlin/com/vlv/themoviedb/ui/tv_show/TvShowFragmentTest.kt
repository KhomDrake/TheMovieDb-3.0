package com.vlv.themoviedb.ui.tv_show

import com.vlv.data.network.NetworkInitializer
import com.vlv.favorite.FavoriteInitializer
import com.vlv.favorite.domain.usecase.TvShowFavoriteUseCase
import com.vlv.movie.presentation.MovieInitializer
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import com.vlv.tv_show.TvShowInitializer
import com.vlv.tv_show.data.repository.TvShowRepository
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<TvShowRepository>(relaxed = true) }
    single { mockk<TvShowFavoriteUseCase>(relaxed = true) }
}

class TvShowFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java,
        TvShowInitializer::class.java,
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