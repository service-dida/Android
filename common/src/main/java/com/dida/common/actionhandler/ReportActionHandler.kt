package com.dida.common.actionhandler

interface ReportActionHandler {
    fun onReportClicked(userId: Long)
    fun onBlockClicked(userId: Long)
}
