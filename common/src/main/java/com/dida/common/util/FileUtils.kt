package com.dida.common.util

import android.content.Context
import android.net.Uri

fun Uri.checkImageSize(context: Context): Boolean {
    val uri = this
    val inputStream = context.contentResolver.openInputStream(uri)
    val bytes = inputStream?.buffered()?.use { it.readBytes() }
    val sizeInMb = bytes?.size?.toDouble()?.div(1024)?.div(1024)

    return !(sizeInMb != null && sizeInMb > 10)
}
