package com.vlv.themoviedb.ui.series

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.series.Series
import com.vlv.common.data.series.toFavorite
import com.vlv.data.common.model.series.SeriesResponse
import com.vlv.favorite.domain.usecase.SeriesFavoriteUseCase
import com.vlv.series.data.repository.SeriesRepository
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.loadObjectFromJson
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SeriesFragmentTest.seriesFragment(func: SeriesFragmentSetup.() -> Unit) =
    SeriesFragmentSetup().apply(func)

class SeriesFragmentSetup : Setup<SeriesFragmentLaunch, SeriesFragmentCheck>, KoinComponent {

    private val repository: SeriesRepository by inject()
    private val useCase: SeriesFavoriteUseCase by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): SeriesFragmentCheck {
        return SeriesFragmentCheck()
    }

    override fun createLaunch(): SeriesFragmentLaunch {
        return SeriesFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<SeriesFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withFavorites() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        coEvery {
            useCase.favorites()
        } returns data.series.map { Series(it).toFavorite() }
    }

    fun withSeriesTrending() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.trendingSeries(any())
        } returns data
    }

    fun withSeriesAiringToday() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.airingToday()
        } returns data
    }

}

class SeriesFragmentLaunch : Launch<SeriesFragmentCheck> {
    override fun createCheck(): SeriesFragmentCheck {
        return SeriesFragmentCheck()
    }
}

class SeriesFragmentCheck : Check, KoinComponent {

    private val repository: SeriesRepository by inject()
    private val useCase: SeriesFavoriteUseCase by inject()

    fun favoritesLoaded() {
        coVerify {
            useCase.favorites()
        }
    }

    fun trendingLoaded() {
        coVerify {
            repository.trendingSeries(any())
        }
    }

    fun airingTodayLoaded() {
        coVerify {
            repository.airingToday()
        }
    }
}