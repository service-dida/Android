package com.dida.common.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.ConnectException
import java.net.URL

fun Uri.checkImageSize(context: Context): Boolean {
    val uri = this
    val inputStream = context.contentResolver.openInputStream(uri)
    val bytes = inputStream?.buffered()?.use { it.readBytes() }
    val sizeInMb = bytes?.size?.toDouble()?.div(1024)?.div(1024)

    return !(sizeInMb != null && sizeInMb > 10)
}

suspend fun Context.urlImageToFile(url: String): File?  {
    return withContext(Dispatchers.IO) {
        try {
            val fileName = "${System.currentTimeMillis()}.jpg"
            val cachePath = "${this@urlImageToFile.cacheDir}/cacheFiles"
            val dir = File(cachePath)

            if (!dir.exists()) {
                dir.mkdirs() // 폴더 없을 경우 폴더 생성
            }

            val fileItem = File("$dir/$fileName")

            val inputStream = URL(url).openStream()
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()

            fileItem.createNewFile()
            val fos = FileOutputStream(fileItem)

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)

            fos.close()

            return@withContext fileItem
        } catch (e: ConnectException) {
            e.printStackTrace()
            return@withContext null
        } catch (e: Exception) {
            e.printStackTrace()
            return@withContext null
        }
    }
}