package com.vlv.configuration.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Text(
                    "Settings",
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }

}