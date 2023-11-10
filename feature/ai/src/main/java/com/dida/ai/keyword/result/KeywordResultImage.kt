package com.dida.ai.keyword.result

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dida.compose.theme.BrandLemon
import com.dida.compose.utils.noRippleClickable
import com.dida.compose.utils.shimmerBrush

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun KeywordResultImages(
    images: List<String>,
    selectedImage: String,
    onClicked: (String) -> Unit
) {
    var showShimmer by remember { mutableStateOf(true) }
    FlowRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 28.dp, start = 16.dp, end = 16.dp),
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (images != KeywordResultViewModel.INITIALIZE_LIST) {
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
                        .noRippleClickable { onClicked(it) }
                }
                AsyncImage(
                    modifier = backgroundModifier
                        .background(shimmerBrush(showShimmer = showShimmer)),
                    model = it,
                    alpha = if (selectedImage != it && selectedImage.isNotBlank()) 0.5f else 1f,
                    contentScale = ContentScale.Crop,
                    contentDescription = "AI 그림 이미지 이미지",
                    onSuccess = { showShimmer = false },
                )
            }
        } else {
            (0..3).forEach { _ ->
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(vertical = 4.dp)
                        .clip(shape = RoundedCornerShape(10.dp))
                        .background(shimmerBrush(showShimmer = showShimmer))
                )
            }
        }
    }
}
