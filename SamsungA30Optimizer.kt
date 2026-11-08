package com.stfashion.creativehouse.utils

import android.app.ActivityManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream
import kotlin.math.max
import kotlin.math.min

/**
 * Hardware-level optimizations specifically crafted for Samsung Galaxy A30 (SM-A305F/FN)
 * and budget 3GB RAM Android devices.
 * Prevents java.lang.OutOfMemoryError during high-resolution garment photo processing.
 */
object SamsungA30Optimizer {

    // Target max viewport dimension for Samsung A30 HD+ (720 x 1560)
    private const val MAX_A30_WIDTH = 1080
    private const val MAX_A30_HEIGHT = 1920

    fun isLowRamDevice(context: Context): Boolean {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.isLowRamDevice
    }

    /**
     * Decodes a sample-sized bitmap from Uri to prevent loading gigantic 48MP/108MP camera
     * photos directly into the 192MB/256MB dalvik heap of the Galaxy A30.
     */
    fun decodeSampledBitmapFromUri(
        context: Context,
        uri: Uri,
        reqWidth: Int = MAX_A30_WIDTH,
        reqHeight: Int = MAX_A30_HEIGHT
    ): Bitmap? {
        return try {
            // Step 1: Decode with inJustDecodeBounds=true to check dimensions without heap allocation
            var input: InputStream? = context.contentResolver.openInputStream(uri)
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }
            BitmapFactory.decodeStream(input, null, options)
            input?.close()

            // Step 2: Calculate inSampleSize (power of 2)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Step 3: Decode with sampled bounds using RGB_565 on low RAM to halve memory usage
            options.inJustDecodeBounds = false
            if (isLowRamDevice(context)) {
                options.inPreferredConfig = Bitmap.Config.RGB_565 // 2 bytes per pixel instead of 4
            } else {
                options.inPreferredConfig = Bitmap.Config.ARGB_8888
            }

            input = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input, null, options)
            input?.close()

            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return max(1, inSampleSize)
    }

    /**
     * Downscales an existing in-memory bitmap if it exceeds Galaxy A30 thresholds.
     */
    fun scaleForA30(src: Bitmap, maxWidth: Int = 1080): Bitmap {
        if (src.width <= maxWidth) return src
        val aspectRatio = src.height.toFloat() / src.width.toFloat()
        val targetHeight = (maxWidth * aspectRatio).toInt()
        return Bitmap.createScaledBitmap(src, maxWidth, targetHeight, true)
    }
}
