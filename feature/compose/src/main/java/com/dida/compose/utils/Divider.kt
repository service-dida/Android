package com.dida.compose.utils

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun Divider8() {
    Spacer(modifier = Modifier.size(8.dp))
}

@Composable
fun Divider10() {
    Spacer(modifier = Modifier.size(10.dp))
}

@Composable
fun Divider12() {
    Spacer(modifier = Modifier.size(12.dp))
}

@Composable
fun Divider16() {
    Spacer(modifier = Modifier.size(16.dp))
}

@Composable
fun VerticalDivider(dp: Int) {
    Spacer(
        modifier = Modifier
            .height(dp.dp)
            .wrapContentWidth()
    )
}

@Composable
fun HorizontalDivider(dp: Int) {
    Spacer(
        modifier = Modifier
            .width(dp.dp)
            .wrapContentHeight()
    )
}

@Composable
fun RowScope.weightDivider(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}

@Composable
fun ColumnScope.weightDivider(weight: Float) {
    Spacer(modifier = Modifier.weight(weight))
}
