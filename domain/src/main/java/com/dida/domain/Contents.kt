package com.dida.domain

data class Contents<T>(
    val page: Int,
    val pageSize: Int,
    val hasNext: Boolean = false,
    var content: List<T>
) {
    val size: Int
        get() = content.size

    val isNotEmpty: Boolean
        get() = content.isNotEmpty()
}
