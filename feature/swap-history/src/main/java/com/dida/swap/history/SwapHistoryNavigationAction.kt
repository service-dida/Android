package com.dida.swap.history

sealed class SwapHistoryNavigationAction  {
    object finishGetSwapHistory : SwapHistoryNavigationAction()
}