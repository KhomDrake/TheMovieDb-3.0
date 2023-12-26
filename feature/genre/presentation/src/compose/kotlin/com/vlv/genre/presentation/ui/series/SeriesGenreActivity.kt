package com.vlv.genre.presentation.ui.series

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.vlv.bondsmith.data.ResponseStatus
import com.vlv.common.ui.extension.handle
import com.vlv.genre.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import org.koin.androidx.compose.koinViewModel

class SeriesGenreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = stringResource(id = R.string.genre_series_toolbar_title)) {
                            finish()
                        }
                    }
                ) {
                    SeriesTabScreen(paddingValues = it)
                }
            }
        }
    }

}

@Composable
fun SeriesTabScreen(
    paddingValues: PaddingValues,
    viewModel: SeriesGenreViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    state.handle(
        success = { genres ->
            SeriesGenreSuccess(
                paddingValues = paddingValues,
                genres = genres
            )
        }
    )
}
