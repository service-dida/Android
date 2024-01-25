package com.dida.notification.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.common.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface5
import com.dida.compose.theme.dpToSp

@Composable
fun NotificationFooterItem() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 41.dp),
        textAlign = TextAlign.Center,
        text = stringResource(id = R.string.alarm_footer_message),
        style = DidaTypography.caption,
        fontSize = dpToSp(dp = 14.dp),
        lineHeight = dpToSp(dp = 21.dp),
        color = Surface5
    )
}
