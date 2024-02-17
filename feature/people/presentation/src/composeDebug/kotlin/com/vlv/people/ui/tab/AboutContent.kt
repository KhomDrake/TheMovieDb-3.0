package com.vlv.people.ui.tab

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.vlv.common.data.people.People
import com.vlv.common.ui.about.AboutList
import com.vlv.common.ui.extension.handle
import org.koin.androidx.compose.koinViewModel

@Composable
fun AboutContent(
    people: People,
    modifier: Modifier = Modifier,
    viewModel: PeopleAboutViewModel = koinViewModel()
) {
    LaunchedEffect(key1 = people.id, block = {
        viewModel.detail(people)
    })

    val state by viewModel.detailState.collectAsState()

    state.handle(
        success = {
            AboutList(
                items = it.about,
                modifier = modifier
            )
        }
    )

}