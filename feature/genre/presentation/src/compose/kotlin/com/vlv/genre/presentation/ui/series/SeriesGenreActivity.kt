package com.vlv.genre.presentation.ui.series

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SeriesGenreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {

            }
        }
    }

}