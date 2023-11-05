package com.vlv.movie.ui.search

import com.vlv.movie.MovieInitializer
import com.vlv.data.network.NetworkInitializer
import com.vlv.data.network.database.TheMovieDbDao
import com.vlv.data.network.repository.MovieRepository
import com.vlv.data.network.repository.SearchRepository
import com.vlv.test.IntentsRule
import com.vlv.test.KoinRule
import io.mockk.mockk
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

private val myModule = module {
    single { mockk<TheMovieDbDao>(relaxed = true) }
    single { mockk<PeopleApi>(relaxed = true) }
    single { mockk<MovieApi>(relaxed = true) }
    single { mockk<SeriesApi>(relaxed = true) }
    single { SearchRepository(get(), get(), get(), get()) }
    single { mockk<MovieRepository>(relaxed = true) }
    single { mockk<MovieDetailRepository>(relaxed = true) }
}

@Ignore("Some tests are running forever, without reason")
class SearchMovieActivityTest {

    @get:Rule
    val intentsRule = IntentsRule()

    @JvmField
    @Rule
    val koinRule = KoinRule(
        listOf(myModule),
        NetworkInitializer::class.java,
        MovieInitializer::class.java
    )

    @Test
    fun whenInitialStateAndEmptyHistory_shouldDisplayedRightInformation() {
        searchMovieActivity {
            withSearchHistory()
            withSearchSuccessful()
        } check {
            initialStateDisplayed()
        }
    }

    @Test
    fun whenInitialStateAndWithHistory_shouldDisplayedRightInformationAndHistory() {
        searchMovieActivity {
            withSearchHistory()
            withSearchSuccessful()
        } check {
            initialStateDisplayed()
            historyDisplayed()
        }
    }

    @Test
    fun withHistory_andClickHistory_shouldSearchHistoryItem() {
        searchMovieActivity {
            withSearchHistory()
            withSearchSuccessful()
        } launch {
            clickHistory(position = 1)
        } check {
            searchedUsingHistory()
        }
    }

    @Test
    fun withoutHistory_andInputSearch_andClickSearch_shouldCalledSearchAndAddToHistory() {
        searchMovieActivity {
            withSearchHistoryEmpty()
            withSearchSuccessful()
        } launch {
            inputText(text = "Avengers")
            clickSearch()
        } check {
            searchCalled(text = "Avengers", times = 1)
            addToHistory()
        }
    }

    @Test
    fun whenSearching_andReturnsEmpty_shouldShowEmptyState() {
        searchMovieActivity {
            withSearchHistoryEmpty()
            withSearchEmpty()
        } launch {
            inputText(text = "Avengers")
            clickSearch()
        } check {
            emptyStateDisplayed()
        }
    }

    @Test
    fun whenSearching_andReturnsError_shouldShowErrorState() {
        searchMovieActivity {
            withSearchHistoryEmpty()
            withSearchFailure()
        } launch {
            inputText(text = "Avengers")
            clickSearch()
        } check {
            errorStateDisplayed()
        }
    }

    @Test
    fun whenShowingErrorState_andClickTryAgain_shouldSearchAgain() {
        searchMovieActivity {
            withSearchHistoryEmpty()
            withSearchFailure()
        } launch {
            inputText(text = "Avengers")
            clickSearch()
            clickTryAgain()
        } check {
            searchCalled(text = "Avengers", times = 2)
        }
    }

}