package com.dida.common.util

import android.net.Uri
import android.os.Build
import org.apache.commons.io.IOUtils
import java.io.FileOutputStream
import java.util.Base64
fun String.toImageUri() : Uri? {
    val encodedData = this
    val file = createTempFile("tmp", ".jpeg")
    val fos = FileOutputStream(file)
    try {
        val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            Base64.getDecoder().decode(encodedData)
            Base64.getMimeDecoder().decode(encodedData)
        } else {
            android.util.Base64.decode(encodedData, android.util.Base64.DEFAULT)
        }
        IOUtils.write(data, fos)
    } finally {
        try {
            fos.close()
        } catch (e: Exception) {
            return null
        }
    }

    return Uri.fromFile(file)
}
