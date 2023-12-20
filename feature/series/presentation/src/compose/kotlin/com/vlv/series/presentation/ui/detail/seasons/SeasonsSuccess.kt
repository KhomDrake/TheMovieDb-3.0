package com.vlv.series.presentation.ui.detail.seasons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vlv.common.ui.HorizontalList
import com.vlv.series.presentation.model.Season

@Composable
fun SeasonsSuccess(
    seasons: List<Season>,
    modifier: Modifier = Modifier
) {
    HorizontalList(
        itemCount = seasons.size,
        content = { index ->
            val season = seasons[index]
            SeasonItem(
                season = season,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        modifier = modifier
    )
}