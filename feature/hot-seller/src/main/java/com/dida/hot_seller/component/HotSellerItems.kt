package com.dida.hot_seller.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.DidaTypography
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Surface1
import com.dida.compose.theme.White
import com.dida.compose.theme.dpToSp
import com.dida.compose.utils.DidaImage
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.VerticalDivider
import com.dida.compose.utils.clickableSingle
import com.dida.domain.main.model.HotSellerPage

@Composable
fun HotSellerItem(
    item: HotSellerPage,
    onFollowButtonClicked: () -> Unit,
    onUserClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .clickableSingle { onUserClicked() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DidaImage(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(100.dp)),
                model = item.memberInfo.profileUrl
            )
            HorizontalDivider(dp = 12)
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = item.memberInfo.memberName,
                    style = DidaTypography.h3,
                    fontSize = dpToSp(dp = 18.dp),
                    color = White
                )
                VerticalDivider(dp = 4)
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "NFT ${item.memberInfo.nftCount}",
                    style = DidaTypography.caption,
                    fontSize = dpToSp(dp = 14.dp),
                    color = White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (!item.memberInfo.me) {
                if (item.memberInfo.followed) FollowingButton { onFollowButtonClicked() }
                else FollowButton { onFollowButtonClicked() }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding()
                .clip(RoundedCornerShape(12)),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item.nftImgUrl.forEach {
                DidaImage(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f),
                    model = it
                )
            }
        }
    }
}

@Composable
fun FollowingButton(
    onFollowButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = BrandLemon,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .background(
                color = BrandLemon,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickableSingle { onFollowButtonClicked() },
        color = BrandLemon
    ) {
        Text(
            text = "팔로잉",
            style = DidaTypography.body1,
            fontSize = dpToSp(dp = 12.dp),
            color = MainBlack
        )
    }
}

@Composable
fun FollowButton(
    onFollowButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = BrandLemon,
                shape = RoundedCornerShape(size = 100.dp)
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickableSingle { onFollowButtonClicked() },
        color = Surface1
    ) {
        Text(
            text = "팔로우",
            style = DidaTypography.body1,
            fontSize = dpToSp(dp = 12.dp),
            color = White
        )
    }
}
