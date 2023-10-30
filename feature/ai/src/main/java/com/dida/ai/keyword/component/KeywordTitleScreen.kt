package com.dida.ai.keyword.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.dida.ai.keyword.KeywordType
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp

@Composable
fun KeywordTitle(type: KeywordType) {
    val annotationString = getAnnotationStringFromType(type = type)
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

private fun getAnnotationStringFromType(type: KeywordType): AnnotatedString {
    return when (type) {
        KeywordType.Product -> {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = BrandLemon)
                ) {
                    append("무엇")
                }
                append("을 그려볼까요?")
            }
        }
        KeywordType.Place -> {
            buildAnnotatedString {
                append("어떤 ")
                withStyle(
                    style = SpanStyle(color = BrandLemon)
                ) {
                    append("장소")
                }
                append("에 있나요?")
            }
        }
        KeywordType.Style -> {
            buildAnnotatedString {
                append("어떤 ")
                withStyle(
                    style = SpanStyle(color = BrandLemon)
                ) {
                    append("스타일")
                }
                append("로 그려드릴까요?")
            }
        }
        KeywordType.Color -> {
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = BrandLemon)
                ) {
                    append("어떤 색")
                }
                append("의 그림으로 \n그려드릴까요?")
            }
        }
    }
}
