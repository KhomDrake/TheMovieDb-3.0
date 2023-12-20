package com.vlv.movie.presentation.ui.detail.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vlv.common.ui.about.AboutList
import com.vlv.movie.presentation.data.MovieDetail

@Composable
fun MovieAboutSuccess(
    data: MovieDetail,
    modifier: Modifier = Modifier
) {
    AboutList(
        items = data.items,
        modifier = modifier
    )
}