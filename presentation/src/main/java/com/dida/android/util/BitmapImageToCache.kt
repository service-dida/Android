package com.dida.android.util

import android.content.Context
import android.graphics.Bitmap
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun BitmapImageToCache(bitmap: Bitmap, context: Context?): File {

    val fileName = "${System.currentTimeMillis()}.jpg"
    val cachePath = "${context?.cacheDir}/cacheFiles"
    val dir = File(cachePath)

    if (dir.exists().not()) {
        dir.mkdirs() // 폴더 없을경우 폴더 생성
    }
    val fileItem = File("$dir/$fileName")
    try {

        fileItem.createNewFile()
        //0KB 파일 생성.

        val fos = FileOutputStream(fileItem) // 파일 아웃풋 스트림

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
        //파일 아웃풋 스트림 객체를 통해서 Bitmap 압축.

        fos.close() // 파일 아웃풋 스트림 객체 close
        return fileItem
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return fileItem

}