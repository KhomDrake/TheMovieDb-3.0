package com.vlv.imperiya.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val ParagraphSize = 12.sp
val ParagraphBigSize = 14.sp
val SubTitleSmallSize = 12.sp
val SubTitleSize = 14.sp
val SubTitleBigSize = 16.sp
val TitleSize = 18.sp
val TitleBigSize = 20.sp

object TheMovieDbTypography {

    val ParagraphStyle: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = ParagraphSize,
            color = Paragraph_Color
        )

    val ParagraphBoldStyle = ParagraphStyle.copy(
        fontWeight = FontWeight.Bold
    )

    val ParagraphBigStyle = ParagraphStyle.copy(
        fontSize = ParagraphBigSize
    )

    val LinkStyle: TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = ParagraphBigSize,
            color = Link
        )

    val ChipStyle : TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = ParagraphSize
        )

    val SubTitleStyle : TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = SubTitleSize
        )

    val SubTitleSmallStyle = SubTitleStyle.copy(
        fontSize = SubTitleSmallSize
    )

    val SubTitleBigStyle = SubTitleStyle.copy(
        color = Sub_Title2_Color,
        fontSize = SubTitleBigSize,
        fontWeight = FontWeight.Bold
    )

    val SubTitleBoldStyle = SubTitleStyle.copy(
        fontWeight = FontWeight.Bold
    )

    val TitleStyle : TextStyle
        get() = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Bold,
            fontSize = TitleSize
        )

    val TitleBigStyle = TitleStyle.copy(
        fontSize = TitleBigSize
    )

}
