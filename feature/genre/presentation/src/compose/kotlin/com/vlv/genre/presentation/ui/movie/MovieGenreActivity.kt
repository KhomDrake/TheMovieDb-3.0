package com.vlv.genre.presentation.ui.movie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.ui.extension.handle
import com.vlv.genre.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.TabItem
import com.vlv.imperiya.core.ui.components.TabRow
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

class MovieGenreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = stringResource(id = R.string.genre_movie_toolbar_title)) {
                            finish()
                        }
                    }
                ) {
                    TabScreen(paddingValues = it)
                }
            }
        }
    }

}

@Composable
fun TabScreen(
    paddingValues: PaddingValues,
    viewModel: MovieGenreViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    state.handle(
        success = { genres ->
            MovieGenreSuccess(
                paddingValues = paddingValues,
                genres = genres
            )
        },
        error = {

        },
        loading = {

        }
    )
}