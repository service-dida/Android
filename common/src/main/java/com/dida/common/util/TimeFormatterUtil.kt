package com.dida.common.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale


fun convertyyyyMMdd(inputDate: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy:MM:dd", Locale.getDefault())

    try {
        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
        return "날짜 변환 오류"
    }
}