package com.dida.compose.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

val Pretendard = androidx.compose.ui.text.font.FontFamily(
    Font(com.dida.common.R.font.pretendard_bold, FontWeight.Bold),
    Font(com.dida.common.R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(com.dida.common.R.font.pretendard_light, FontWeight.Light),
    Font(com.dida.common.R.font.pretendard_medium, FontWeight.Medium),
    Font(com.dida.common.R.font.pretendard_regular, FontWeight.Normal)
)

val DidaTypography = Typography (
    h1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp
    ),

    h2 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),

    h3 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),

    subtitle1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),

    body1 = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),

    button = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),

    caption = TextStyle(
        fontFamily = Pretendard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )

)

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }
