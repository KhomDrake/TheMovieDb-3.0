package com.vlv.common.ui

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable

@Composable
fun Carousel(
    count: Int,
    onClickItem: (Int) -> Unit,
    carouselItem: @Composable (Int) -> Unit
) {
    LazyRow {
        items(count) {
            carouselItem.invoke(it)
        }
    }
}

@Composable
fun CarouselItem(
    title: String,
) {

}