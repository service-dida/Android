package com.dida.domain.main.model

data class AppVersion(
    val versionId: Long,
    val version: String,
    val changes: String,
    val essentialUpdate: Boolean
)