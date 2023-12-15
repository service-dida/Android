package com.dida.ownership_history.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dida.common.util.convertyyyyMMdd
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.Surface6
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.LineDivider
import com.dida.domain.main.model.OwnershipHistory

@Composable
fun OwnershipHistoryItem(
    modifier: Modifier,
    nameColor : Color,
    item: OwnershipHistory
) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = convertyyyyMMdd(item.ownerDate),
                    modifier = modifier
                        .padding(),
                    style = DidaTypography.subtitle2,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Surface6
                )
                Text(
                    text = item.ownerName,
                    style = DidaTypography.subtitle2,
                    fontSize = dpToSp(dp = 14.dp),
                    color = nameColor
                )
            }

            Text(
                text = "${item.price} dida",
                style = DidaTypography.subtitle2,
                fontSize = dpToSp(dp = 14.dp),
                color = BrandLemon
            )
        }
    }
}

@Composable
@Preview(heightDp = 100)
fun OwnershipHistoryItemPreview() {
    OwnershipHistoryItem(
        modifier = Modifier,
        nameColor = White,
        item = OwnershipHistory("2023-12-08T11:32:11.049677",2,"서승환",0.0))
}