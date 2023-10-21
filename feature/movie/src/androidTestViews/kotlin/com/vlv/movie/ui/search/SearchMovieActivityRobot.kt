package com.vlv.movie.ui.search

import android.net.http.HttpException
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.platform.app.InstrumentationRegistry
import com.squareup.moshi.Moshi
import com.vlv.common.ui.route.toMovieSearch
import com.vlv.network.api.MovieApi
import com.vlv.network.data.movie.MoviesResponse
import com.vlv.network.database.TheMovieDbDao
import com.vlv.network.database.data.History
import com.vlv.network.database.data.HistoryType
import com.vlv.test.Check
import com.vlv.test.Launch
import com.vlv.test.Setup
import com.vlv.test.checkViewOnRecyclerViewPosition
import com.vlv.test.clickIgnoreConstraint
import com.vlv.test.clickOnRecyclerViewItem
import com.vlv.test.hasHint
import com.vlv.test.hasText
import com.vlv.test.isDisplayed
import com.vlv.test.isNotDisplayed
import com.vlv.test.loadObjectFromJson
import com.vlv.test.typeText
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun SearchMovieActivityTest.searchMovieActivity(func: SearchMovieActivitySetup.() -> Unit) =
    SearchMovieActivitySetup().apply(func)

class SearchMovieActivitySetup : Setup<SearchMovieActivityLaunch, SearchMovieActivityCheck>, KoinComponent {

    private val dao: TheMovieDbDao by inject()
    private val movieApi: MovieApi by inject()
    private val moshi: Moshi by inject()

    override fun createCheck(): SearchMovieActivityCheck {
        return SearchMovieActivityCheck()
    }

    override fun createLaunch(): SearchMovieActivityLaunch {
        return SearchMovieActivityLaunch()
    }

    override fun setupLaunch() {
        coEvery {
            dao.insertHistory(any())
        } returns Unit

        coEvery {
            dao.deleteHistory(any())
        } returns Unit

        ActivityScenario.launch<SearchMovieActivity>(
            InstrumentationRegistry.getInstrumentation().context.toMovieSearch()
        )
    }

    fun withSearchHistoryEmpty() {
        every {
            dao.historyByType(HistoryType.MOVIE)
        } returns MutableLiveData<List<History>>().apply {
            postValue(listOf())
        }
    }

    fun withSearchHistory() {
        every {
            dao.historyByType(HistoryType.MOVIE)
        } returns MutableLiveData<List<History>>().apply {
            postValue(listOf(
                History("Spiderman", HistoryType.MOVIE),
                History("Superman", HistoryType.MOVIE),
                History("Bacate", HistoryType.MOVIE)
            ))
        }
    }

    fun withSearchSuccessful() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_search.json",
            moshi
        ) ?: return

        coEvery {
            movieApi.search(any(), 1)
        } returns data
    }

    fun withSearchFailure() {
        coEvery {
            movieApi.search(any(), 1)
        } throws HttpException("Error Test", null)
    }

    fun withSearchEmpty() {
        val data = loadObjectFromJson<MoviesResponse>(
            InstrumentationRegistry.getInstrumentation().context,
            "movie_search_empty.json",
            moshi
        ) ?: return

        coEvery {
            movieApi.search(any(), 1)
        } returns data
    }

}

class SearchMovieActivityLaunch : Launch<SearchMovieActivityCheck> {
    override fun createCheck(): SearchMovieActivityCheck {
        return SearchMovieActivityCheck()
    }

    fun clickHistory(position: Int) {
        com.vlv.common.R.id.history_items.clickOnRecyclerViewItem(
            position
        )
    }

    fun inputText(text: String) {
        androidx.appcompat.R.id.search_src_text.typeText(text)
    }

    fun clickSearch() {
        androidx.appcompat.R.id.search_go_btn.clickIgnoreConstraint()
    }

    fun clickTryAgain() {
        com.vlv.imperiya.R.id.try_again_button.clickIgnoreConstraint()
    }

}

class SearchMovieActivityCheck : Check, KoinComponent {

    private val movieApi: MovieApi by inject()
    private val dao: TheMovieDbDao by inject()

    fun initialStateDisplayed() {
        androidx.appcompat.R.id.search_src_text.hasHint("Search for movies")
        com.vlv.common.R.id.search.isDisplayed()
        com.vlv.common.R.id.items.isNotDisplayed()
        com.vlv.common.R.id.warning_view.isNotDisplayed()
        com.vlv.common.R.id.state_view.isNotDisplayed()
        com.vlv.common.R.id.loading.isNotDisplayed()
    }

    fun historyDisplayed() {
        com.vlv.common.R.id.history_items.apply {
            isDisplayed()
            checkViewOnRecyclerViewPosition(
                1,
                ViewMatchers.withText("Spiderman"),
                com.vlv.common.R.id.title
            )
            checkViewOnRecyclerViewPosition(
                2,
                ViewMatchers.withText("Superman"),
                com.vlv.common.R.id.title
            )
            checkViewOnRecyclerViewPosition(
                3,
                ViewMatchers.withText("Bacate"),
                com.vlv.common.R.id.title
            )
        }
    }

    fun searchedUsingHistory() {
        coVerify {
            movieApi.search(
                "Spiderman", 1
            )
        }
    }

    fun searchCalled(text: String, times: Int) {
        coVerify(exactly = times) {
            movieApi.search(
                text, 1
            )
        }
    }

    fun addToHistory() {
        coVerify {
            dao.insertHistory(any())
        }
    }

    fun emptyStateDisplayed() {
        com.vlv.common.R.id.search.isDisplayed()
        com.vlv.common.R.id.state_view.isDisplayed()

        com.vlv.imperiya.R.id.title_state.hasText("No movie was found")
        com.vlv.imperiya.R.id.body_state.isNotDisplayed()

        com.vlv.common.R.id.items.isNotDisplayed()
        com.vlv.common.R.id.warning_view.isNotDisplayed()
        com.vlv.common.R.id.loading.isNotDisplayed()
    }

    fun errorStateDisplayed() {
        com.vlv.common.R.id.search.isDisplayed()
        com.vlv.common.R.id.warning_view.isDisplayed()

        com.vlv.imperiya.R.id.title.hasText("Failed to load")
        com.vlv.imperiya.R.id.body.hasText("Check your internet connection, wait a few moments and click in try again")

        com.vlv.common.R.id.state_view.isNotDisplayed()
        com.vlv.common.R.id.items.isNotDisplayed()
        com.vlv.common.R.id.loading.isNotDisplayed()
    }

}