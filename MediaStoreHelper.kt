package com.stfashion.creativehouse.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import java.io.OutputStream

object MediaStoreHelper {

    /**
     * Saves reconstructed fashion photo directly to Android MediaStore Pictures album.
     * Works on Android 11 (API 30) with scoped storage.
     */
    fun saveImageToGallery(
        context: Context,
        bitmap: Bitmap,
        title: String = "ST_Fashion_${System.currentTimeMillis()}"
    ): Uri? {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$title.jpg")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "${Environment.DIRECTORY_PICTURES}/STFashionCreativeHouse")
                put(MediaStore.MediaColumns.IS_PENDING, 1)
            }
        }

        val resolver = context.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        uri?.let { destinationUri ->
            try {
                val outputStream: OutputStream? = resolver.openOutputStream(destinationUri)
                outputStream?.use { out ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(destinationUri, contentValues, null, null)
                }
                return destinationUri
            } catch (e: Exception) {
                e.printStackTrace()
                resolver.delete(destinationUri, null, null)
            }
        }
        return null
    }
}
