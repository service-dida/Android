package com.dida.ai.keyword.result

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Gray05
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp

@Composable
fun KeywordResultTitle() {
    Text(
        text = stringResource(id = R.string.keyword_result_title),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp),
        style = DidaTypography.subtitle1,
        fontSize = dpToSp(dp = 28.dp),
        color = White
    )
}

@Composable
fun KeywordResultMessage() {
    Text(
        text = stringResource(id = R.string.keyword_result_description),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 16.dp, end = 16.dp),
        style = DidaTypography.body1,
        fontSize = dpToSp(dp = 16.dp),
        color = Gray05
    )
}
