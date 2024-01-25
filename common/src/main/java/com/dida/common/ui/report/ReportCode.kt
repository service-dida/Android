package com.dida.common.ui.report

enum class ReportCode(val title: String, var message: String) {
    SPAM("스팸 및 도배 내용", "스팸 및 도배 내용"),
    ADVISE("광고 및 홍보 내용", "광고 및 홍보 내용"),
    SEXUAL("음란물 또는 성적인 내용", "음란물 또는 성적인 내용"),
    PRIVACY("개인정보 노출", "개인정보 노출"),
    OTHER("기타", "")
}
