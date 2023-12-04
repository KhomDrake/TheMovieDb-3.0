package com.vlv.configuration.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.stringResource
import com.vlv.configuration.presentation.R
import com.vlv.configuration.presentation.ui.widget.SettingsContent
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SettingsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(
                            title = stringResource(id = R.string.configuration_toolbar_title)
                        ) {
                           finish()
                        }
                    }
                ) {
                    SettingsContent(paddingValues = it)
                }
            }
        }
    }

}
