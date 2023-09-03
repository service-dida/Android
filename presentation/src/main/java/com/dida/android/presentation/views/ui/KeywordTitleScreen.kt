package com.dida.android.presentation.views.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp

@Composable
fun KeywordProductTitle() {
    val annotationString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(color = BrandLemon)
        ) {
            append("무엇")
        }
        append("을 그려볼까요?")
    }
    Text(
        text = annotationString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        style = DidaTypography.h3,
        fontSize = dpToSp(dp = 28.dp),
        color = White
    )
}

@Composable
fun KeywordPlaceTitle() {
    val annotationString = buildAnnotatedString {
        append("어떤 ")
        withStyle(
            style = SpanStyle(color = BrandLemon)
        ) {
            append("장소")
        }
        append("에 있나요?")
    }
    Text(
        text = annotationString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        style = DidaTypography.h3,
        fontSize = dpToSp(dp = 28.dp),
        color = White
    )
}

@Composable
fun KeywordStyleTitle() {
    val annotationString = buildAnnotatedString {
        append("어떤 ")
        withStyle(
            style = SpanStyle(color = BrandLemon)
        ) {
            append("스타일")
        }
        append("로 그려드릴까요?")
    }
    Text(
        text = annotationString,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        style = DidaTypography.h3,
        fontSize = dpToSp(dp = 28.dp),
        color = White
    )
}
