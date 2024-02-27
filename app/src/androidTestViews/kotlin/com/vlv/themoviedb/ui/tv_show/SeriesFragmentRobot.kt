package com.vlv.themoviedb.ui.tv_show

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.tv_show.TvShow
import com.vlv.common.data.tv_show.toFavorite
import com.vlv.data.common.model.tvshow.TvShowsResponse
import com.vlv.data.database.data.Favorite
import com.vlv.favorite.domain.usecase.TvShowFavoriteUseCase
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.loadObjectFromJson
import com.vlv.tv_show.data.repository.TvShowRepository
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SeriesFragmentTest.seriesFragment(func: SeriesFragmentSetup.() -> Unit) =
    SeriesFragmentSetup().apply(func)

class SeriesFragmentSetup : Setup<SeriesFragmentLaunch, SeriesFragmentCheck>, KoinComponent {

    private val repository: TvShowRepository by inject()
    private val useCase: TvShowFavoriteUseCase by inject()
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
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        every {
            useCase.favorites()
        } returns bondsmith<List<Favorite>>()
            .apply {
                setData(
                    data.tvShows.map { TvShow(it).toFavorite() }
                )
            }
    }

    fun withSeriesTrending() {
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        every {
            repository.tvShowsTrending(any())
        } returns bondsmith<TvShowsResponse>()
            .apply {
                setData(data)
            }
    }

    fun withSeriesAiringToday() {
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        every {
            repository.airingToday()
        } returns bondsmith<TvShowsResponse>()
            .setData(data)
    }

}

class SeriesFragmentLaunch : Launch<SeriesFragmentCheck> {
    override fun createCheck(): SeriesFragmentCheck {
        return SeriesFragmentCheck()
    }
}

class SeriesFragmentCheck : Check, KoinComponent {

    private val repository: TvShowRepository by inject()
    private val useCase: TvShowFavoriteUseCase by inject()

    fun favoritesLoaded() {
        coVerify {
            useCase.favorites()
        }
    }

    fun trendingLoaded() {
        coVerify {
            repository.tvShowsTrending(any())
        }
    }

    fun airingTodayLoaded() {
        coVerify {
            repository.airingToday()
        }
    }
}