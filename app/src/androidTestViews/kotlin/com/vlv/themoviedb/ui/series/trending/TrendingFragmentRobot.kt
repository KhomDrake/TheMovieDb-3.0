package com.vlv.themoviedb.ui.series.trending

import android.content.res.Resources
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.intent.matcher.BundleMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.data.series.SeriesListType
import com.vlv.common.ui.route.SERIES_LISTING_TYPE_EXTRA
import com.vlv.data.network.model.series.SeriesResponse
import com.vlv.data.network.repository.SeriesRepository
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
import io.mockk.coEvery
import io.mockk.coVerify
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun TrendingFragmentTest.trendingFragment(func: TrendingFragmentSetup.() -> Unit) =
    TrendingFragmentSetup().apply(func)

class TrendingFragmentSetup :
    Setup<TrendingFragmentLaunch, TrendingFragmentCheck>, KoinComponent {

    private val repository: SeriesRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
    }

    override fun createLaunch(): TrendingFragmentLaunch {
        return TrendingFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<TrendingFragment>(
            themeResId = com.vlv.imperiya.core.R.style.Imperiya_Theme
        )
    }

    fun withTrendingSuccess() {
        val data = loadObjectFromJson<SeriesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "series_list.json",
            moshi
        ) ?: return

        coEvery {
            repository.trendingSeries(any())
        } returns data
    }

    fun withTrendingEmpty() {
        coEvery {
            repository.trendingSeries(any())
        } returns SeriesResponse(
            1,
            1,
            1,
            listOf()
        )
    }

    fun withTrendingFails() {
        coEvery {
            repository.trendingSeries(any())
        } throws Resources.NotFoundException()
    }

}

class TrendingFragmentLaunch : Launch<TrendingFragmentCheck> {
    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
    }

    fun clickSeries(position: Int) {
        mockIntent("SERIES_DETAIL", targetContext = true)
        R.id.series.clickOnRecyclerViewItem(position)
    }

    fun clickSeeAll() {
        mockIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TRENDING)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }
}

class TrendingFragmentCheck : Check, KoinComponent {

    private val repository: SeriesRepository by inject()

    fun seriesDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")
        R.id.indicator.isDisplayed()

        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.series.apply {
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

    fun seriesTrendingOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(SERIES_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(SeriesListType.TRENDING)),
        )
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.series.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        com.vlv.imperiya.core.R.id.title_state.hasText("None tv shows found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.series.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        com.vlv.imperiya.core.R.id.small_warning_title.hasText("Failed to load trending tv shows")
        com.vlv.imperiya.core.R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        com.vlv.imperiya.core.R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun seriesTrendingLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.trendingSeries(any())
        }
    }

}