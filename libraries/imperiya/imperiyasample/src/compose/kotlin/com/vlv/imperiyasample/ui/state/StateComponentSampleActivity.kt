package com.vlv.imperiyasample.ui.state

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.R
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.StateView
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiyasample.ui.search.SearchSample

class StateComponentSampleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = "State Components") {
                            finish()
                        }
                    }
                ) {
                    StateComponent(paddingValues = it)
                }
            }
        }
    }
}

private class StateComponent(
    val title: String? = null,
    val body: String? = null,
    @DrawableRes
    val icon: Int = 0
)

@Composable
fun StateComponent(paddingValues: PaddingValues) {
    val components = listOf(
        StateComponent(
            title = "Failure title you put it here",
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable
        ),
        StateComponent(
            body = "Failure body you put it here",
            icon = R.drawable.ic_heart_enable
        ),
        StateComponent(
            title = "Failure title you put it here",
            icon = R.drawable.ic_heart_enable
        )
    )

    LazyColumn(
        modifier = Modifier
            .padding(top = paddingValues.calculateTopPadding() + 8.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        content = {
            items(components) { component ->
                StateView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    icon = component.icon,
                    title = component.title,
                    body = component.body
                )
            }
        }
    )
}