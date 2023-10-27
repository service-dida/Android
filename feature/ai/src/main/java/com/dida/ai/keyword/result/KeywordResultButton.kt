package com.dida.ai.keyword.result

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Gray05
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.clickableSingle

@Composable
fun RestartKeyword(
    onClicked: () -> Unit
) {
    Text(
        text = stringResource(id = R.string.restart_keyword),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickableSingle { onClicked() },
        style = DidaTypography.button,
        fontSize = dpToSp(dp = 16.dp),
        color = Gray05,
        textAlign = TextAlign.Center
    )
}

@Composable
fun KeywordResultButton(
    isSelected: Boolean,
    onDownloadClicked: () -> Unit,
    onCreateNftClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
    ) {
        Surface(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(16.dp)
                .clickableSingle { onDownloadClicked() }
        ) {
            Image(
                painter = painterResource(id = com.dida.common.R.drawable.ic_download), 
                contentDescription = "다운로드 이미지",
                colorFilter = ColorFilter.tint(if (isSelected) White else Surface4)
            )
        }
        HorizontalDivider(dp = 12)
        Surface(
            modifier = Modifier
                .weight(1f)
                .clip(shape = RoundedCornerShape(10.dp))
                .padding(vertical = 18.dp)
                .clickableSingle { onCreateNftClicked() }
        ) {
            Text(
                text = stringResource(id = R.string.create_nft),
                modifier = Modifier.weight(1f),
                style = DidaTypography.h1,
                fontSize = dpToSp(dp = 16.dp),
                color = White,
                textAlign = TextAlign.Center
            )
        }
    }
}
