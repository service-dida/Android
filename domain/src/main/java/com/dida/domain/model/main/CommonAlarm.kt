package com.dida.domain.model.main

data class CommonAlarm(
    val alarmId: Long,
    val type: String,
    val id: Long,
    val watched: Boolean,
    val date: String
)
