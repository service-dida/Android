package com.dida.compose.common

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp

val DidaBoldTypography = DidaTypography.h3
val DidaSemiBoldTypography = DidaTypography.subtitle1
val DidaMediumTypography = DidaTypography.body1
val DidaRegularTypography = DidaTypography.caption

@Composable
fun DidaBoldText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 22,
    lineHeight: Int = fontSize + 4,
    color: Color = White,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        modifier = modifier,
        style = DidaBoldTypography,
        text = text,
        textAlign = textAlign,
        fontSize = dpToSp(dp = fontSize.dp),
        lineHeight = dpToSp(dp = lineHeight.dp),
        color = color,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun DidaSemiBoldText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 21,
    lineHeight: Int = fontSize + 4,
    color: Color = White,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        modifier = modifier,
        style = DidaSemiBoldTypography,
        text = text,
        textAlign = textAlign,
        fontSize = dpToSp(dp = fontSize.dp),
        lineHeight = dpToSp(dp = lineHeight.dp),
        color = color,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun DidaMediumText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 15,
    lineHeight: Int = fontSize + 4,
    color: Color = White,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        modifier = modifier,
        style = DidaMediumTypography,
        text = text,
        textAlign = textAlign,
        fontSize = dpToSp(dp = fontSize.dp),
        lineHeight = dpToSp(dp = lineHeight.dp),
        color = color,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout
    )
}

@Composable
fun DidaRegularText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Int = 14,
    lineHeight: Int = fontSize + 4,
    color: Color = White,
    textAlign: TextAlign = TextAlign.Start,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) {
    Text(
        modifier = modifier,
        style = DidaRegularTypography,
        text = text,
        textAlign = textAlign,
        fontSize = dpToSp(dp = fontSize.dp),
        lineHeight = dpToSp(dp = lineHeight.dp),
        color = color,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines,
        onTextLayout = onTextLayout
    )
}
