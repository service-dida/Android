package com.dida.android.presentation.views.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dida.ai.R
import com.dida.compose.theme.Blue
import com.dida.compose.theme.BlueGreen
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Green
import com.dida.compose.theme.GreenYellow
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Mono
import com.dida.compose.theme.Orange
import com.dida.compose.theme.OrangeRed
import com.dida.compose.theme.Red
import com.dida.compose.theme.RedViolet
import com.dida.compose.theme.Surface2
import com.dida.compose.theme.Violet
import com.dida.compose.theme.VioletBlue
import com.dida.compose.theme.White
import com.dida.compose.theme.Yellow
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.clickableSingle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Keywords(
    keywords: List<String>,
    selectKeyword: String = "",
    onKeywordClicked: (keyword: String) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(keywords.size) { position ->
            if (keywords[position] == selectKeyword) SelectKeywordItem(keyword = keywords[position])
            else KeywordItem(keyword = keywords[position], onKeywordClicked = onKeywordClicked)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StyleKeywords(
    keywords: List<Pair<Int, String>>,
    selectKeyword: String = "",
    onKeywordClicked: (keyword: String) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        maxItemsInEachRow = 3
    ) {
        repeat(keywords.size) { position ->
            if (keywords[position].second == selectKeyword) {
                SelectStyleKeywordItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.9f),
                    imageUrl = keywords[position].first,
                    keyword = keywords[position].second
                )
            } else {
                StyleKeywordItem(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(0.9f),
                    imageUrl = keywords[position].first,
                    keyword = keywords[position].second,
                    onKeywordClicked = { onKeywordClicked(it) }
                )
            }
        }
    }
}

val colors = listOf(
    Pair(Red, "Red"), Pair(OrangeRed, "Orange Red"), Pair(Orange, "Orange"), Pair(Yellow, "Yellow"),
    Pair(GreenYellow, "Green Yellow"), Pair(Green, "Green"), Pair(BlueGreen, "Blue Green"), Pair(Blue, "Blue"),
    Pair(VioletBlue, "Violet Blue"), Pair(Violet, "Violet"), Pair(RedViolet, "Red Violet"), Pair(Mono, "Mono")
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorKeywords(
    keywords: List<Pair<Color, String>> = colors,
    selectKeyword: String = "",
    onKeywordClicked: (keyword: String) -> Unit
) {
    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(keywords.size) { position ->
            if (selectKeyword.isBlank()) {
                ColorKeywordItem(color = keywords[position].first, keyword = keywords[position].second, onKeywordClicked = { onKeywordClicked(it) })
            } else {
                if (keywords[position].second == selectKeyword) {
                    ColorKeywordItem(color = keywords[position].first, keyword = keywords[position].second, onKeywordClicked = { })
                } else {
                    Surface2ColorKeyword2Item(keyword = keywords[position].second, onKeywordClicked = { onKeywordClicked(it) })
                }
            }
        }
    }
}

@Composable
fun WriteKeyword(
    onButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .border(
                width = 1.dp,
                color = Surface2,
                shape = RoundedCornerShape(30.dp)
            )
            .clickableSingle { onButtonClicked() },
        color = MainBlack
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.write_keyword),
                fontSize = dpToSp(dp = 16.dp),
                style = DidaTypography.h3,
                color = White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.size(6.dp))
            Icon(
                painter = painterResource(id = com.dida.common.R.drawable.ic_write_fill),
                contentDescription = "직접 입력 버튼",
                tint = White
            )
        }
    }
}

@Composable
fun KeywordItem(
    keyword: String,
    onKeywordClicked: (keyword: String) -> Unit
) {
    Surface(
        color = Surface2,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(keyword) }
        ) {
            Text(
                text = keyword,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SelectKeywordItem(
    keyword: String
) {
    Surface(
        color = BrandLemon,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = keyword,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = MainBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun StyleKeywordItem(
    modifier: Modifier,
    imageUrl: Int,
    keyword: String,
    onKeywordClicked: (keyword: String) -> Unit
) {
    Surface(
        color = Surface2,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable { onKeywordClicked(keyword) }
    ) {
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentDescription = "유저 이미지",
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.Bottom)
                .padding(9.dp),
            text = keyword,
            style = DidaTypography.h3,
            fontSize = dpToSp(dp = 16.dp),
            color = White,
        )
    }
}

@Composable
fun SelectStyleKeywordItem(
    modifier: Modifier,
    imageUrl: Int,
    keyword: String
) {
    Surface(
        color = Surface2,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(vertical = 4.dp)
            .border(
                width = 1.dp,
                color = BrandLemon,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        AsyncImage(
            modifier = modifier,
            model = imageUrl,
            contentDescription = "유저 이미지",
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = modifier
                .wrapContentHeight(Alignment.Bottom)
                .padding(9.dp),
            text = keyword,
            style = DidaTypography.h3,
            fontSize = dpToSp(dp = 16.dp),
            color = White,
        )
    }
}

@Composable
fun ColorKeywordItem(
    color: Color,
    keyword: String,
    onKeywordClicked: (keyword: String) -> Unit
) {
    Surface(
        color = color,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(keyword) }
        ) {
            Text(
                text = keyword,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = MainBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun Surface2ColorKeyword2Item(
    keyword: String,
    onKeywordClicked: (keyword: String) -> Unit
) {
    Surface(
        color = Surface2,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(keyword) }
        ) {
            Text(
                text = keyword,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}
