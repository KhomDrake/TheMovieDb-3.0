package com.vlv.imperiya.core.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RevealDotIndicator1(
    count: Int,
    pagerState: LazyListState,
    activeColor: Color = Color.White,
) {
    val circleSpacing = 8.dp
    val circleSize = 20.dp
    val innerCircle = 14.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp), contentAlignment = Alignment.Center
    ) {
        val width = (circleSize * count) + (circleSpacing * (count - 1))

        Canvas(modifier = Modifier.width(width = width)) {
            val distance = (circleSize + circleSpacing).toPx()
            val centerY = size.height / 2
            val startX = circleSpacing.toPx()

            repeat(count) {
                val pageOffset = pagerState.firstVisibleItemScrollOffset.toFloat()

                val scale = 0.2f.coerceAtLeast(1 - pageOffset.absoluteValue)
                val outlineStroke = Stroke(2.dp.toPx())

                val x = startX + (it * distance)
                val circleCenter = Offset(x, centerY)
                val innerRadius = innerCircle.toPx() / 2
                val radius = (circleSize.toPx() * scale) / 2

                drawCircle(
                    color = activeColor,
                    style = outlineStroke,
                    center = circleCenter,
                    radius = radius
                )

                drawCircle(
                    color = activeColor,
                    center = circleCenter,
                    radius = innerRadius
                )
            }
        }
    }
}