package com.vlv.genre.ui.series

import android.content.res.Resources.NotFoundException
import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.genre.R
import com.vlv.genre.ui.movie.GENRE_ID_EXTRA
import com.vlv.data.network.model.series.SeriesResponse
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkIntent
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import com.vlv.test.mockIntent
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SeriesByGenreFragmentTest.seriesByGenreFragment(func: SeriesByGenreFragmentSetup.() -> Unit) =
    SeriesByGenreFragmentSetup().apply(func)

class SeriesByGenreFragmentSetup :
    Setup<SeriesByGenreFragmentLauncher, SeriesByGenreFragmentCheck>, KoinComponent {

    private val discoverApi: DiscoverApi by inject()
    private val moshi: Moshi by inject()

    private var arguments = bundleOf()

    override fun createCheck(): SeriesByGenreFragmentCheck {
        return SeriesByGenreFragmentCheck()
    }

    override fun createLaunch(): SeriesByGenreFragmentLauncher {
        return SeriesByGenreFragmentLauncher()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<SeriesByGenreFragment>(
            fragmentArgs = arguments,
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withGenreId() {
        arguments = bundleOf(
            GENRE_ID_EXTRA to 42
        )
    }

    fun withoutGenreId() {
        arguments = bundleOf()
    }

    fun withSeriesByGenreSuccess() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_by_genre.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverSeries(
                42,
                1
            )
        } returns data
    }

    fun withSeriesByGenreEmpty() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_by_genre_empty.json",
            moshi
        ) ?: return

        coEvery {
            discoverApi.discoverSeries(
                42,
                1
            )
        } returns data
    }

    fun withSeriesByGenreError() {
        coEvery {
            discoverApi.discoverSeries(
                42,
                1
            )
        } throws NotFoundException()
    }

}

class SeriesByGenreFragmentLauncher : Launch<SeriesByGenreFragmentCheck> {
    override fun createCheck(): SeriesByGenreFragmentCheck {
        return SeriesByGenreFragmentCheck()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }

    fun clickSeries(position: Int) {
        mockIntent("SERIES_DETAIL")
        R.id.items.clickOnRecyclerViewItem(position)
    }

}

class SeriesByGenreFragmentCheck : Check, KoinComponent {

    private val discoverApi: DiscoverApi by inject()

    fun seriesByGenreLoaded(times: Int) {
        coVerify(exactly = times) {
            discoverApi.discoverSeries(42, 1)
        }
    }

    fun errorStateDisplayed() {
        R.id.error.isDisplayed()
        R.id.items.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()

        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun seriesDisplayed() {
        R.id.error.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()

        R.id.items.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Rick and Morty"),
                com.vlv.ui.R.id.series_title
            )
        }
    }

    fun emptyStateDisplayed() {
        R.id.error.isNotDisplayed()
        R.id.items.isNotDisplayed()
        R.id.empty_state.isDisplayed()
        R.id.shimmer.isNotDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("No tv show found")
    }

    fun seriesDetailOpened() {
        checkIntent("SERIES_DETAIL")
    }

}