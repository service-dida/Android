package com.dida.android.presentation.views.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp

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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SelectKeywords(
    keywords: List<String>
) {
    if (keywords.isEmpty()) {
        Spacer(modifier = Modifier.height(80.dp))
    } else {
        FlowRow(
            modifier = Modifier
                .height(80.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(keywords.size) { position ->
                SelectedKeywordItem(keyword = keywords[position])
            }
        }
    }
}

@Composable
fun SelectedKeywordItem(
    keyword: String
) {
    Surface(
        color = White,
        shape = RoundedCornerShape(30.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
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
