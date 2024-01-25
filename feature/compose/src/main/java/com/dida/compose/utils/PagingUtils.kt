package com.dida.compose.utils

import androidx.compose.foundation.lazy.LazyListState

fun LazyListState.reachEnd() = layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
