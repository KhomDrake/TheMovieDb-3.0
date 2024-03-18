package com.vlv.themoviedb.ui.tv_show.airingtoday

import android.content.res.Resources.NotFoundException
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.bondsmith.bondsmith
import com.vlv.common.data.tv_show.TvShowListType
import com.vlv.common.route.TV_SHOW_LISTING_TYPE_EXTRA
import com.vlv.data.common.model.tvshow.TvShowsResponse
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
import com.vlv.themoviedb.R
import com.vlv.tv_show.data.repository.TvShowRepository
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun AiringTodayFragmentTest.airingTodayFragment(func: AiringTodayFragmentSetup.() -> Unit) =
    AiringTodayFragmentSetup().apply(func)

class AiringTodayFragmentSetup :
    Setup<AiringTodayFragmentLaunch, AiringTodayFragmentCheck>, KoinComponent {

    private val repository: TvShowRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): AiringTodayFragmentCheck {
        return AiringTodayFragmentCheck()
    }

    override fun createLaunch(): AiringTodayFragmentLaunch {
        return AiringTodayFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<AiringTodayFragment>(
            themeResId = R.style.Imperiya_Theme
        )
    }

    fun withAiringTodaySuccess() {
        val data = loadObjectFromJson<TvShowsResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        every {
            repository.airingToday()
        } returns bondsmith<TvShowsResponse>()
            .apply {
                setData(data)
            }
    }

    fun withAiringTodayEmpty() {
        every {
            repository.airingToday()
        } returns bondsmith<TvShowsResponse>()
            .apply {
                setData(
                    TvShowsResponse(
                        1,
                        1,
                        1,
                        listOf()
                    )
                )
            }
    }

    fun withAiringTodayFails() {
        every {
            repository.airingToday()
        } returns bondsmith<TvShowsResponse>()
            .apply {
                setError(NotFoundException())
            }
    }
}

class AiringTodayFragmentLaunch : Launch<AiringTodayFragmentCheck> {
    override fun createCheck(): AiringTodayFragmentCheck {
        return AiringTodayFragmentCheck()
    }

    fun clickSeries(position: Int) {
        mockIntent("SERIES_DETAIL", targetContext = true)
        R.id.tv_shows.clickOnRecyclerViewItem(position)
    }

    fun clickSeeAll() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TV_SHOW_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(TvShowListType.AIRING_TODAY)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }

    fun clickTryAgain() {
        R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }
}

class AiringTodayFragmentCheck : Check, KoinComponent {

    private val repository: TvShowRepository by inject()

    fun seriesDisplayed() {
        R.id.list_title.hasText("Airing Today")
        R.id.see_all.hasText("See All")
        R.id.indicator.isDisplayed()

        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.tv_shows.apply {
            checkViewOnRecyclerViewPosition(
                0,
                ViewMatchers.withText("Tagesschau"),
                R.id.series_title
            )
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Elas por Elas"),
                R.id.series_title
            )
        }
    }

    fun seriesDetailOpened() {
        checkIntent("SERIES_DETAIL", targetContext = true)
    }

    fun seriesAiringTodayOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TV_SHOW_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(TvShowListType.AIRING_TODAY)),
        )
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Airing Today")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.tv_shows.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        R.id.title_state.hasText("None tv shows found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Airing Today")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.tv_shows.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        R.id.small_warning_title.hasText("Failed to load tv shows")
        R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun seriesAiringTodayLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.airingToday()
        }
    }

}