package com.dida.ai.keyword.result

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.MainBlack
import com.dida.compose.utils.DidaImage
import com.dida.compose.utils.HorizontalDivider
import com.dida.compose.utils.VerticalDivider

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordResultImages(
    images: List<String>,
    selectedImage: String,
    onClicked: (String) -> Unit
) {
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp, start = 16.dp, end = 16.dp),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        images.forEach {
            val backgroundModifier = if (selectedImage == it) {
                Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(vertical = 4.dp)
                    .border(width = 1.dp, color = BrandLemon, shape = RoundedCornerShape(10.dp))
                    .clip(shape = RoundedCornerShape(10.dp))
            } else {
                Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(vertical = 4.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .clickable { onClicked(it) }
            }
            DidaImage(
                modifier = backgroundModifier,
                model = it,
                alpha = if (selectedImage != it) 0.5f else 1f
            )
        }
    }
}
