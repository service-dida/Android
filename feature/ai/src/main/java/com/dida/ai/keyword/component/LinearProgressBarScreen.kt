package com.dida.ai.keyword.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.Surface2

@Composable
fun CustomLinearProgressBar(
    progress: Float
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp),
            backgroundColor = Surface2,
            color = BrandLemon,
            progress = progress
        )
    }
}
