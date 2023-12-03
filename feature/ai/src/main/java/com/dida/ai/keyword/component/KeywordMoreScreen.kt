package com.dida.ai.keyword.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.LineSurface
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.clickableSingle

@Composable
fun KeywordMore(
    onButtonClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickableSingle { onButtonClicked() }
    ) {
        Text(
            text = stringResource(id = R.string.more_keyword),
            style = DidaTypography.body1,
            fontSize = dpToSp(dp = 14.dp),
            color = LineSurface
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = com.dida.common.R.drawable.ic_random),
            contentDescription = "초기화"
        )
    }
}
