package com.dida.notification.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.Surface5
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.LineDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.WeightDivider
import com.dida.compose.utils.clickableSingle
import com.dida.domain.main.model.Alarm
import com.dida.domain.main.model.AlarmType

@Composable
fun NotificationItem(
    alarm: Alarm,
    onAlarmClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().clickableSingle { onAlarmClicked() }
    ) {
        VerticalDivider(dp = 16)
        NotificationTitle(alarm = alarm)
        NotificationMessage(alarm = alarm)
        VerticalDivider(dp = 6)
        NotificationDate(alarm = alarm)
        VerticalDivider(dp = 16)
        LineDivider(color = Surface4, height = 1)
    }
}

@Composable
fun NotificationTitle(
    alarm: Alarm
) {
    val titleRes = when (alarm.type) {
        AlarmType.FOLLOW -> stringResource(id = com.dida.common.R.string.follow_notification_title)
        AlarmType.COMMENT -> stringResource(id = com.dida.common.R.string.comment_notification_title)
        AlarmType.LIKE -> stringResource(id = com.dida.common.R.string.like_notification_title)
        AlarmType.SALE -> stringResource(id = com.dida.common.R.string.sale_notification_title)
    }
    Text(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        text = titleRes,
        style = DidaTypography.caption,
        fontSize = dpToSp(dp = 14.dp),
        lineHeight = dpToSp(dp = 21.dp),
        color = if (alarm.watched) Surface5 else BrandLemon
    )
}

@Composable
fun NotificationMessage(
    alarm: Alarm
) {
    val titleRes = when (alarm.type) {
        AlarmType.FOLLOW -> stringResource(id = com.dida.common.R.string.follow_notification_message)
        AlarmType.COMMENT -> stringResource(id = com.dida.common.R.string.comment_notification_message)
        AlarmType.LIKE -> stringResource(id = com.dida.common.R.string.like_notification_message)
        AlarmType.SALE -> stringResource(id = com.dida.common.R.string.sale_notification_message)
    }
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
    ) {
        Text(
            modifier = Modifier.width(318.dp),
            text = titleRes,
            style = DidaTypography.body1,
            fontSize = dpToSp(dp = 16.dp),
            lineHeight = dpToSp(dp = 24.dp),
            color = if (alarm.watched) Surface5 else White
        )
        WeightDivider(weight = 1f)
        Icon(
            painter = painterResource(id = com.dida.common.R.drawable.ic_arrow_right),
            contentDescription = "더보기 버튼"
        )
    }
}

@Composable
fun NotificationDate(
    alarm: Alarm
) {
    Text(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        text = alarm.date,
        style = DidaTypography.subtitle1,
        fontSize = dpToSp(dp = 12.dp),
        color = Surface5
    )
}
