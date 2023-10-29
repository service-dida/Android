package com.dida.common.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.BufferedInputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * https://www.geeksforgeeks.org/download-image-from-url-in-android/
 **/

fun stringToBitmap(string: String): Bitmap? {
    val url: URL = mStringToURL(string)!!
    val connection: HttpURLConnection?
    try {
        connection = url.openConnection() as HttpURLConnection
        connection.connect()
        val inputStream: InputStream = connection.inputStream
        val bufferedInputStream = BufferedInputStream(inputStream)
        return BitmapFactory.decodeStream(bufferedInputStream)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

// Function to convert string to URL
fun mStringToURL(string: String): URL? {
    try {
        return URL(string)
    } catch (e: MalformedURLException) {
        e.printStackTrace()
    }
    return null
}

// Function to save image on the device.
// Refer: https://www.geeksforgeeks.org/circular-crop-an-image-and-save-it-to-the-file-in-android/
fun Context.saveMediaToStorage(bitmap: Bitmap?): Boolean {
    val filename = "${System.currentTimeMillis()}.jpg"
    var fos: OutputStream? = null
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        this.contentResolver?.also { resolver ->
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg")
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            }
            val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            fos = imageUri?.let { resolver.openOutputStream(it) }
        }
    } else {
        val imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val image = File(imagesDir, filename)
        fos = FileOutputStream(image)
    }

    fos?.use {
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
        return true
    }
    return false
}
