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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dida.ai.R
import com.dida.ai.keyword.Keyword
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
fun DefaultKeywords(
    keywords: List<Keyword.Default>,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        repeat(keywords.size) { position ->
            DefaultKeywordItem(
                selected = position == selectedIndex,
                item = keywords[position],
                onKeywordClicked = {
                    onKeywordClicked(it)
                    selectedIndex = position
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun StyleKeywords(
    keywords: List<Keyword.Style>,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        maxItemsInEachRow = 3
    ) {
        repeat(keywords.size) { position ->
            StyleKeywordItem(
                selected =  position == selectedIndex,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(0.9f),
                item = keywords[position],
                onKeywordClicked = {
                    onKeywordClicked(it)
                    selectedIndex = position
                }
            )
        }
    }
}

val colors = listOf(
    Keyword.Color(color = Red, word = "Red"),
    Keyword.Color(color = OrangeRed, word = "Orange Red"),
    Keyword.Color(color = Orange, word ="Orange"),
    Keyword.Color(color = Yellow, word = "Yellow"),
    Keyword.Color(color = GreenYellow, word = "Green Yellow"),
    Keyword.Color(color = Green, word = "Green"),
    Keyword.Color(color = BlueGreen, word = "Blue Green"),
    Keyword.Color(color = Blue, word = "Blue"),
    Keyword.Color(color = VioletBlue, word = "Violet Blue"),
    Keyword.Color(color = Violet, word = "Violet"),
    Keyword.Color(color = RedViolet, word = "Red Violet"),
    Keyword.Color(color = Mono, word = "Mono")
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ColorKeywords(
    items: List<Keyword.Color> = colors,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    var selectedIndex by remember { mutableStateOf(-1) }

    FlowRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
    ) {
        if (selectedIndex == -1) {
            repeat(items.size) { position ->
                ColorKeywordItem(
                    item = items[position],
                    onKeywordClicked = {
                        onKeywordClicked(it)
                        selectedIndex = position
                    }
                )
            }
        } else {
            repeat(items.size) { position ->
                SelectedColorKeywordItem(
                    selected = selectedIndex == position,
                    item = items[position],
                    onKeywordClicked = {
                        onKeywordClicked(it)
                        selectedIndex = position
                    }
                )
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
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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
fun DefaultKeywordItem(
    selected: Boolean,
    item: Keyword.Default,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    val backgroundColor = if (selected) BrandLemon else Surface2
    val textColor = if (selected) MainBlack else White
    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(item) }
        ) {
            Text(
                text = item.word,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = textColor,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun StyleKeywordItem(
    selected: Boolean,
    modifier: Modifier,
    item: Keyword.Style,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    val backgroundModifier = if (selected) {
        modifier.padding(vertical = 4.dp).border(width = 1.dp, color = BrandLemon, shape = RoundedCornerShape(10.dp))
    } else {
        modifier.padding(vertical = 4.dp).clickable { onKeywordClicked(item) }
    }
    Surface(
        color = Surface2,
        shape = RoundedCornerShape(10.dp),
        modifier = backgroundModifier
    ) {
        AsyncImage(
            modifier = modifier,
            model = item.imageUrl,
            contentDescription = "사진 이미지",
            contentScale = ContentScale.FillBounds,
            alpha = 0.7f
        )
        Text(
            modifier = modifier
                .fillMaxSize()
                .wrapContentHeight(Alignment.Bottom)
                .padding(9.dp),
            text = item.word,
            style = DidaTypography.body1,
            fontSize = dpToSp(dp = 16.dp),
            color = White,
        )
    }
}

@Composable
fun ColorKeywordItem(
    item: Keyword.Color,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    Surface(
        color = item.color,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(item) }
        ) {
            Text(
                text = item.word,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = MainBlack,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun SelectedColorKeywordItem(
    selected: Boolean,
    item: Keyword.Color,
    onKeywordClicked: (keyword: Keyword) -> Unit
) {
    Surface(
        color = if (selected) item.color else Surface2,
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { onKeywordClicked(item) }
        ) {
            Text(
                text = item.word,
                style = DidaTypography.h3,
                fontSize = dpToSp(dp = 16.dp),
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}
