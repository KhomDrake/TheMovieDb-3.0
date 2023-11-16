package com.vlv.imperiyasample.ui.topbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class TopBarSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
            }
        }
    }

}