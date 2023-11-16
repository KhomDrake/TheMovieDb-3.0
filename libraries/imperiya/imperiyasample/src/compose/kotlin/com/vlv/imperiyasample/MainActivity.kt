package com.vlv.imperiyasample

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography
import com.vlv.imperiyasample.ui.ColorsActivity
import com.vlv.imperiyasample.ui.DynamicColorsActivity
import com.vlv.imperiyasample.ui.search.SearchSampleActivity
import com.vlv.imperiyasample.ui.state.StateComponentSampleActivity
import com.vlv.imperiyasample.ui.texts.TextSampleActivity
import com.vlv.imperiyasample.ui.topbar.TopBarSampleActivity
import com.vlv.imperiyasample.ui.warningview.WarningsComponentSampleActivity
import kotlin.reflect.KClass

class Component(
    val title: String,
    val activity: KClass<*>,
    val bundle: Bundle = bundleOf()
)

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activities = listOf(
            Component(
                "Colors",
                ColorsActivity::class
            ),
            Component(
                "Dynamic Colors Dark",
                DynamicColorsActivity::class,
                bundleOf(
                    "DARK_MODE" to true
                )
            ),
            Component(
                "Dynamic Colors Light",
                DynamicColorsActivity::class,
                bundleOf(
                    "DARK_MODE" to false
                )
            ),
            Component(
                "Search",
                SearchSampleActivity::class
            ),
            Component(
                "State Component",
                StateComponentSampleActivity::class
            ),
            Component(
                "Texts",
                TextSampleActivity::class
            ),
            Component(
                "TopBar",
                TopBarSampleActivity::class
            ),
            Component(
                "Warning View",
                WarningsComponentSampleActivity::class
            ),
        )

        setContent {
            TheMovieDbAppTheme {
                ButtonList(
                    "Sample Views",
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

@Preview
@Composable
fun MainPreview() {
    TheMovieDbAppTheme {
        ButtonList(
            "Sample Views",
            listOf(),
            {

            },
            {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonList(
    title: String,
    activities: List<Component>,
    onClick: (Component) -> Unit,
    onBackButton: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DefaultTopBar(title = title, onBackButton)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(top = it.calculateTopPadding())
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp),
                text = "Components:",
                style = TheMovieDbTypography.TitleStyle,
                color = MaterialTheme.colorScheme.onBackground
            )
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp
                ),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items = activities) { component ->
                    ComponentItem(
                        component = component,
                        onClick = onClick,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }
            }
        }
    }


}

@Composable
fun ComponentItem(
    component: Component,
    onClick: (Component) -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = modifier,
        onClick = {onClick.invoke(component)},
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ) {
        Text(
            text = component.title,
            style = TheMovieDbTypography.SubTitleBoldStyle,
            color = MaterialTheme.colorScheme.onTertiary
        )
    }
}