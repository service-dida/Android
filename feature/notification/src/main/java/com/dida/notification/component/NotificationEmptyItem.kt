package com.dida.notification.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.common.R
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface5
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.VerticalDivider

@Composable
fun NotificationEmptyItem() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Icon(painter = painterResource(
            id = R.drawable.ic_notification_empty),
            contentDescription = "알림 Empty 이미지"
        )
        VerticalDivider(dp = 12)
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.alarm_empty_message),
            style = DidaTypography.subtitle1,
            fontSize = dpToSp(dp = 16.dp),
            lineHeight = dpToSp(dp = 24.dp),
            color = Surface5
        )
    }
}
