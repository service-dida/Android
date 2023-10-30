package com.dida.ai.keyword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.ai.keyword.Keyword
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.HorizontalDivider
import kotlinx.coroutines.launch

@Composable
fun SelectKeywordTitle(
    isSelected: Boolean
) {
    Text(
        text = if (isSelected) stringResource(id = R.string.select_keyword) else stringResource(id = R.string.no_keyword),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        style = DidaTypography.body1,
        fontSize = dpToSp(dp = 16.dp),
        color = White
    )
}

@Composable
fun SelectKeywords(
    keywords: List<Keyword>
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = keywords) {
        coroutineScope.launch {
            listState.scrollToItem(keywords.size)
        }
    }

    if (keywords.isEmpty()) {
        Spacer(modifier = Modifier.height(80.dp))
    } else {
        LazyRow(
            modifier = Modifier
                .height(80.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            state = listState
        ) {
            item { HorizontalDivider(dp = 10) }
            items(keywords.size) {
                if(keywords[it].word != "") SelectedKeywordItem(keyword = keywords[it].word)
            }
            item { HorizontalDivider(dp = 10) }
        }
    }
}

@Composable
fun SelectedKeywordItem(
    keyword: String
) {
    Surface(
        modifier = Modifier.padding(vertical = 16.dp),
        color = White,
        shape = RoundedCornerShape(30.dp),
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
