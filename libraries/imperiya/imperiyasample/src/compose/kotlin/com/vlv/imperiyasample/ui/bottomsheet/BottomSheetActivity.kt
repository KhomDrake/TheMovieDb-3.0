package com.vlv.imperiyasample.ui.bottomsheet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.vlv.imperiya.core.ui.components.DefaultTopBar
import com.vlv.imperiya.core.ui.components.TheMovieDbModalBottomSheet
import com.vlv.imperiya.core.ui.theme.TheMovieDbAppTheme
import com.vlv.imperiya.core.ui.theme.TheMovieDbTypography

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

                    Column(
                        modifier = Modifier
                            .padding(top = it.calculateTopPadding())
                    ) {
                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),
                            onClick = {
                                showModal = true
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.tertiary
                            )
                        ) {
                            Text(
                                text = "Show Modal",
                                style = TheMovieDbTypography.SubTitleBoldStyle,
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        }

                        if(showModal) {
                            TheMovieDbModalBottomSheet(
                                onDismissRequest = {
                                    showModal = false
                                },
                                modifier = Modifier,
                                sheetState = sheetState,
                                content = {
                                    Text(
                                        text = WindowInsets.Companion.navigationBars.toString(),
                                        style = TheMovieDbTypography.SubTitleBoldStyle,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        modifier = Modifier
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }

}