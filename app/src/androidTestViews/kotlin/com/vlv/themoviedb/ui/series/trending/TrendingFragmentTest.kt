package com.vlv.themoviedb.ui.series.trending

import com.vlv.favorite.FavoriteInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.series.SeriesInitializer
import com.vlv.series.data.repository.SeriesRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import com.vlv.themoviedb.ui.MainInitializer
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module { 
    single { mockk<SeriesRepository>(relaxed = true) }
}

class TrendingFragmentTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        SeriesInitializer::class.java,
        FavoriteInitializer::class.java,
        MainInitializer::class.java
    )

    @Test
    fun withSeriesTrendingSuccess_shouldShowSeries() {
        trendingFragment {
            withTrendingSuccess()
        } check {
            seriesDisplayed()
        }
    }

    @Test
    fun withSeriesTrendingSuccess_andClickSeries_shouldOpenSeriesDetail() {
        trendingFragment {
            withTrendingSuccess()
        } launch {
            clickSeries(position = 0)
        } check {
            seriesDetailOpened()
        }
    }

    @Test
    fun withSeriesTrendingSuccess_andClickSeeAll_shouldOpenSeriesTrending() {
        trendingFragment {
            withTrendingSuccess()
        } launch {
            clickSeeAll()
        } check {
            seriesTrendingOpened()
        }
    }

    @Test
    fun withSeriesTrendingEmpty_shouldShowEmptyState() {
        trendingFragment {
            withTrendingEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withSeriesTrendingFails_shouldShowErrorState() {
        trendingFragment {
            withTrendingFails()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andCLickTryAgain_shouldLoadSeriesTrendingAgain() {
        trendingFragment {
            withTrendingFails()
        } launch {
            clickTryAgain()
        } check {
            seriesTrendingLoaded(times = 2)
        }
    }

}