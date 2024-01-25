package com.dida.ai.keyword.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dida.ai.R
import com.dida.compose.common.DidaBoldText
import com.dida.compose.theme.BrandLemon
import com.dida.compose.theme.MainBlack
import com.dida.compose.theme.Surface4
import com.dida.compose.theme.Surface6

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NextButton(
    hasNext: Boolean,
    onButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = if (hasNext) BrandLemon else Surface4,
        shape = RoundedCornerShape(8.dp),
        onClick = { if (hasNext) onButtonClicked() else Unit }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DidaBoldText(
                text = stringResource(id = R.string.next),
                fontSize = 16,
                color = if (hasNext) MainBlack else Surface6,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrawButton(
    hasNext: Boolean,
    onButtonClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        color = if (hasNext) BrandLemon else Surface4,
        shape = RoundedCornerShape(8.dp),
        onClick = { if (hasNext) onButtonClicked() else Unit }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DidaBoldText(
                text = stringResource(id = R.string.draw),
                fontSize = 16,
                color = if (hasNext) MainBlack else Surface6,
                textAlign = TextAlign.Center
            )
        }
    }
}
