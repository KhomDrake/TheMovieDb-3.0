package com.vlv.imperiyasample.ui.warningview

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiyasample.ButtonList
import com.vlv.imperiyasample.Component

class WarningsComponentSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                val activities = listOf(
                    Component(
                        "Small Warning",
                        SmallWarningComponentActivity::class
                    ),
                    Component(
                        "Warning",
                        WarningListComponentActivity::class
                    ),
                )

                ButtonList(
                    "Warning Components",
                    activities,
                    onClick = {
                        startActivity(
                            Intent(
                                this,
                                it.activity.java
                            ).apply {
                                putExtras(it.bundle)
                            }
                        )
                    }
                ) {
                    finish()
                }
            }
        }
    }

}