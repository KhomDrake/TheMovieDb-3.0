package com.vlv.genre.ui.series

import android.content.res.Resources.NotFoundException
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.route.toTvShowGenre
import com.vlv.data.common.model.genre.GenresResponse
import com.vlv.data.common.model.tvshow.TvShowsResponse
import com.vlv.genre.R
import com.vlv.genre.data.api.DiscoverApi
import com.vlv.genre.data.api.GenresApi
import com.vlv.genre.presentation.ui.tv_show.TvShowGenreActivity
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkTextTabLayoutPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun TvShowGenreActivityTest.seriesGenreActivity(func: SeriesGenreActivitySetup.() -> Unit) =
    SeriesGenreActivitySetup().apply(func)

class SeriesGenreActivitySetup :
    Setup<SeriesGenreActivityLauncher, SeriesGenreActivityCheck>, KoinComponent {

    private val discoverApi: DiscoverApi by inject()
    private val genreApi: GenresApi by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): SeriesGenreActivityCheck {
        return SeriesGenreActivityCheck()
    }

    override fun createLaunch(): SeriesGenreActivityLauncher {
        return SeriesGenreActivityLauncher()
    }

    override fun setupLaunch() {
        ActivityScenario.launch<TvShowGenreActivity>(
            InstrumentationRegistry.getInstrumentation().context.toTvShowGenre()
        )
    }

    fun withGenreSuccess() {
        val data = loadObjectFromJson<GenresResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_genres.json",
            moshi
        ) ?: return
        coEvery {
            genreApi.tvShowGenres()
        } returns data
    }

    fun withGenreError() {
        coEvery {
            genreApi.tvShowGenres()
        } throws NotFoundException()
    }

    fun withSeriesByGenreSuccess() {
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_by_genre.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverTvShow(
                42,
                1
            )
        } returns data
    }

}

class SeriesGenreActivityLauncher : Launch<SeriesGenreActivityCheck> {
    override fun createCheck(): SeriesGenreActivityCheck {
        return SeriesGenreActivityCheck()
    }

    fun clickTryAgain() {
        R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }
}

class SeriesGenreActivityCheck : Check, KoinComponent {

    private val genresApi: GenresApi by inject()

    fun genresDisplayed() {
        "Tv shows by genre".isDisplayed()

        R.id.tabs.isDisplayed()
        R.id.layout.isDisplayed()
        R.id.loading.isNotDisplayed()

        R.id.tabs.apply {
            checkTextTabLayoutPosition("Action & Adventure", 0)
            checkTextTabLayoutPosition("Animation", 1)
            checkTextTabLayoutPosition("Comedy", 2)
            checkTextTabLayoutPosition("Crime", 3)
        }
    }

    fun errorStateDisplayed() {
        R.id.tabs.isNotDisplayed()
        R.id.layout.isNotDisplayed()
        R.id.loading.isNotDisplayed()
        R.id.error.isDisplayed()

        R.id.small_warning_title.hasText("Failed to load genres")
        R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun genresLoaded(times: Int) {
        coVerify(exactly = times) {
            genresApi.tvShowGenres()
        }
    }
}