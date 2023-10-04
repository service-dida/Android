package com.dida.soldout

enum class Period(val period: Int, val text: String) {
    SEVEN_DAY(period = 7, text = "일주일 이내"),
    ONE_MONTH(period = 30, text = "1개월"),
    SIX_MONTH(period = 180, text = "6개월"),
    ONE_YEAR(period = 365, text = "올 한 해"),
}
