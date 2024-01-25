package com.dida.domain.main.model

data class Alarm(
    val alarmId: Long,
    val type: AlarmType,
    val id: Long,
    val watched: Boolean,
    val date: String
)

enum class AlarmType {
    SALE, LIKE, FOLLOW, COMMENT
}
