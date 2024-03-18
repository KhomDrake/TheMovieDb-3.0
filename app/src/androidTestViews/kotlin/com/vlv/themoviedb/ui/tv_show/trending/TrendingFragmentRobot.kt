package com.vlv.themoviedb.ui.tv_show.trending

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

fun TrendingFragmentTest.trendingFragment(func: TrendingFragmentSetup.() -> Unit) =
    TrendingFragmentSetup().apply(func)

class TrendingFragmentSetup :
    Setup<TrendingFragmentLaunch, TrendingFragmentCheck>, KoinComponent {

    private val repository: TvShowRepository by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
    }

    override fun createLaunch(): TrendingFragmentLaunch {
        return TrendingFragmentLaunch()
    }

    override fun setupLaunch() {
        launchFragmentInContainer<TrendingFragment>(
            themeResId = R.style.Imperiya_Theme
        )
    }

    fun withTrendingSuccess() {
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

    fun withTrendingEmpty() {
        every {
            repository.tvShowsTrending(any())
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

    fun withTrendingFails() {
        every {
            repository.tvShowsTrending(any())
        } returns bondsmith<TvShowsResponse>()
            .apply {
                setError(NotFoundException())
            }
    }

}

class TrendingFragmentLaunch : Launch<TrendingFragmentCheck> {
    override fun createCheck(): TrendingFragmentCheck {
        return TrendingFragmentCheck()
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
            IntentMatchers.hasExtras(BundleMatchers.hasValue(TvShowListType.TRENDING)),
        )

        R.id.see_all.clickIgnoreConstraint()
    }

    fun clickTryAgain() {
        R.id.small_warning_try_again_button.clickIgnoreConstraint()
    }
}

class TrendingFragmentCheck : Check, KoinComponent {

    private val repository: TvShowRepository by inject()

    fun seriesDisplayed() {
        R.id.list_title.hasText("Trending now")
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

    fun seriesTrendingOpened() {
        checkIntent(
            "SERIES_LISTING",
            true,
            IntentMatchers.hasExtras(BundleMatchers.hasKey(TV_SHOW_LISTING_TYPE_EXTRA)),
            IntentMatchers.hasExtras(BundleMatchers.hasValue(TvShowListType.TRENDING)),
        )
    }

    fun emptyStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.tv_shows.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.error_state.isNotDisplayed()

        R.id.empty_state.isDisplayed()

        R.id.title_state.hasText("None tv shows found")
    }

    fun errorStateDisplayed() {
        R.id.list_title.hasText("Trending now")
        R.id.see_all.hasText("See All")

        R.id.indicator.isNotDisplayed()
        R.id.tv_shows.isNotDisplayed()
        R.id.shimmer.isNotDisplayed()
        R.id.empty_state.isNotDisplayed()

        R.id.error_state.isDisplayed()
        R.id.small_warning_title.hasText("Failed to load trending tv shows")
        R.id.small_warning_body.hasText("Check your internet connection, wait a few moments and click in try again button")
        R.id.small_warning_try_again_button.hasText("Try again")
    }

    fun seriesTrendingLoaded(times: Int) {
        coVerify(exactly = times) {
            repository.tvShowsTrending(any())
        }
    }

}