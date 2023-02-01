package com.dida.domain.model.nav.swap_history

import java.text.SimpleDateFormat
import java.util.*

data class SwapHistory(
    val sendCoinType: String,
    val receiveCoinType: String,
    val sendAmount: Double,
    val time: String
){
    fun timeToDayAndTime() : Pair<String,String> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        val date = inputFormat.parse(time)

        val dayFormat = SimpleDateFormat("MM.dd", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val day = dayFormat.format(date)
        val time = timeFormat.format(date)

        return Pair(day, time)
    }
}

