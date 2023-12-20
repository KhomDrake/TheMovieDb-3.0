package com.vlv.imperiyasample.ui.warningview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.SmallWarningView
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme

class SmallWarningComponentActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = "Small Warning Sample") {
                            finish()
                        }
                    }
                ) { paddingValues ->
                    val components = listOf(
                        SmallWarningItem(
                            "Failure title you put it here",
                            "Failure body you put it here",
                            "Text button"
                        ),
                        SmallWarningItem(
                            "Failure title you put it here",
                            "Failure body you put it here",
                            "Text button",
                            shouldShowInfoIcon = false
                        ),
                        SmallWarningItem(
                            "Failure title you put it here",
                            null,
                            "Text button",
                            shouldShowInfoIcon = false
                        ),
                        SmallWarningItem(
                            null,
                            "Failure body you put it here",
                            "Text button",
                            shouldShowInfoIcon = false
                        )
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 16.dp,
                            vertical = 12.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(
                                top = paddingValues.calculateTopPadding()
                            ),
                        content = {
                            items(components) { item ->
                                SmallWarningView(
                                    title = item.title,
                                    body = item.body,
                                    linkActionText = item.link,
                                    showIconInfo = item.shouldShowInfoIcon,
                                    onClickLink = {
                                        Toast.makeText(
                                            this@SmallWarningComponentActivity,
                                            "Title: ${item.link}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }

}

class SmallWarningItem(
    val title: String?,
    val body: String?,
    val link: String,
    val shouldShowInfoIcon: Boolean = false
)