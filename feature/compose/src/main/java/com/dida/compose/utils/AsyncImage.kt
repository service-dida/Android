package com.dida.compose.utils

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage

@Composable
fun DidaImage(
    modifier: Modifier,
    model: String?,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null
) {
    if (model.isNullOrBlank()) {
        Image(
            modifier = modifier,
            painter = painterResource(id = com.dida.common.R.mipmap.img_dida_logo_foreground),
            contentDescription = "이미지"
        )
    } else {
        AsyncImage(
            modifier = modifier,
            model = model,
            contentScale = ContentScale.Crop,
            contentDescription = "유저 이미지",
            alpha = alpha,
            colorFilter = colorFilter
        )
    }

}
