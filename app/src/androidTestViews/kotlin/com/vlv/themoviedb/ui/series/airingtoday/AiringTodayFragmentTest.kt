package com.vlv.themoviedb.ui.series.airingtoday

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

class AiringTodayFragmentTest {

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
    fun withSeriesAiringTodaySuccess_shouldShowSeries() {
        airingTodayFragment {
            withAiringTodaySuccess()
        } check {
            seriesDisplayed()
        }
    }

    @Test
    fun withSeriesAiringTodaySuccess_andClickSeries_shouldOpenSeriesDetail() {
        airingTodayFragment {
            withAiringTodaySuccess()
        } launch {
            clickSeries(position = 0)
        } check {
            seriesDetailOpened()
        }
    }

    @Test
    fun withSeriesAiringTodaySuccess_andClickSeeAll_shouldOpenSeriesAiringToday() {
        airingTodayFragment {
            withAiringTodaySuccess()
        } launch {
            clickSeeAll()
        } check {
            seriesAiringTodayOpened()
        }
    }

    @Test
    fun withSeriesAiringTodayEmpty_shouldShowEmptyState() {
        airingTodayFragment {
            withAiringTodayEmpty()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun withSeriesAiringTodayFails_shouldShowErrorState() {
        airingTodayFragment {
            withAiringTodayFails()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun withErrorState_andCLickTryAgain_shouldLoadSeriesAiringTodayAgain() {
        airingTodayFragment {
            withAiringTodayFails()
        } launch {
            clickTryAgain()
        } check {
            seriesAiringTodayLoaded(times = 2)
        }
    }

}