package com.dida.ownership_history.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dida.common.util.convertyyyyMMdd
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.Surface6
import com.dida.compose.theme.Surface8
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.LineDivider
import com.dida.domain.main.model.OwnershipHistory

@Composable
fun OwnershipHistoryItem(
    modifier: Modifier = Modifier,
    currentOwner: Boolean,
    item: OwnershipHistory
) {
    val nameColor = if (currentOwner) White else Surface8
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text(
                    text = convertyyyyMMdd(item.ownerDate),
                    modifier = modifier
                        .padding(),
                    style = DidaTypography.caption,
                    fontSize = dpToSp(dp = 14.dp),
                    color = Surface6
                )
                Text(
                    text = item.ownerName,
                    style = DidaTypography.subtitle1,
                    fontSize = dpToSp(dp = 14.dp),
                    color = nameColor
                )
            }

            Text(
                text = "${item.price} dida",
                style = DidaTypography.subtitle1,
                fontSize = dpToSp(dp = 14.dp),
                color = BrandLemon
            )
        }
        if (!currentOwner) {
            LineDivider(Surface4, 1)
        }
    }
}

@Composable
@Preview(heightDp = 100)
fun OwnershipHistoryItemPreview() {
    OwnershipHistoryItem(
        modifier = Modifier,
        currentOwner = true,
        item = OwnershipHistory("2023-12-08T11:32:11.049677", 2, "서승환", 0.0)
    )
}
