package com.vlv.imperiyasample.ui.bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.TheMovieDbModalBottomSheet
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

enum class ButtonsBottomSheet {
    TESTING_PADDING,
    NORMAL_LIST,
    BIG_LIST
}

class BottomSheetActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheMovieDbAppTheme {
                Scaffold(
                    topBar = {
                        DefaultTopBar(title = "Bottom Sheet") {
                            finish()
                        }
                    }
                ) {
                    val sheetState = rememberModalBottomSheetState()
                    var showModal by remember { mutableStateOf(false) }
                    var selectedItem by remember {
                        mutableStateOf(ButtonsBottomSheet.TESTING_PADDING)
                    }

                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                            .fillMaxSize()
                    ) {
                        LazyColumn(
                            content = {
                                items(ButtonsBottomSheet.values()) {
                                    Button(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        onClick = {
                                            showModal = true
                                            selectedItem = it
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = MaterialTheme.colorScheme.tertiary
                                        )
                                    ) {
                                        Text(
                                            text = it.name,
                                            style = TheMovieDbTypography.SubTitleBoldStyle,
                                            color = MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }
                            }
                        )


                        if(showModal) {
                            TheMovieDbModalBottomSheet(
                                onDismissRequest = {
                                    showModal = false
                                },
                                modifier = Modifier,
                                sheetState = sheetState,
                                content = {
                                    when(selectedItem) {
                                        ButtonsBottomSheet.TESTING_PADDING -> {
                                            ListBottomSheet(count = 1)
                                        }
                                        ButtonsBottomSheet.NORMAL_LIST -> {
                                            ListBottomSheet(count = 6)
                                        }
                                        ButtonsBottomSheet.BIG_LIST -> {
                                            ListBottomSheet(count = 40)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ListBottomSheet(count: Int) {
    LazyColumn(
        content = {
            items(count) {
                Text(
                    text = WindowInsets.Companion.navigationBars.toString(),
                    style = TheMovieDbTypography.SubTitleBoldStyle,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        },
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
    )
}
