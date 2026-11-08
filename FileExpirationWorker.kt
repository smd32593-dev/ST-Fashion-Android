package com.stfashion.creativehouse.workers

import android.content.Context
import androidx.work.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Android WorkManager Worker that enforces the Strict 30-Day Automatic File Deletion policy.
 * Runs periodically (every 24 hours), checks application-managed media files,
 * and permanently purges files that have reached their 30-day lifespan.
 *
 * Rule: Viewing, editing, or exporting a file NEVER resets its original 30-day expiration date.
 */
class FileExpirationWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    companion object {
        private const val WORK_TAG = "st_file_expiration_worker"
        const val EXPIRATION_DURATION_MS = 30L * 24 * 60 * 60 * 1000 // 30 Days strictly

        fun schedulePeriodicCleanup(context: Context) {
            val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build()

            val periodicRequest = PeriodicWorkRequestBuilder<FileExpirationWorker>(
                1, TimeUnit.DAYS,
                1, TimeUnit.HOURS // flex interval
            )
                .setConstraints(constraints)
                .addTag(WORK_TAG)
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 15, TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "STFashion30DayCleanup",
                ExistingPeriodicWorkPolicy.KEEP,
                periodicRequest
            )
        }

        fun triggerImmediateCleanup(context: Context) {
            val oneTimeRequest = OneTimeWorkRequestBuilder<FileExpirationWorker>()
                .addTag("immediate_cleanup")
                .build()
            WorkManager.getInstance(context).enqueue(oneTimeRequest)
        }
    }

    override suspend fun doWork(): Result {
        return try {
            val now = System.currentTimeMillis()
            val metadataFile = File(context.filesDir, "media_metadata.json")
            if (!metadataFile.exists()) {
                return Result.success()
            }

            val content = metadataFile.readText()
            val jsonArray = JSONArray(content)
            val survivingArray = JSONArray()
            var deletedCount = 0

            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val filePath = item.optString("filePath")
                val createdAt = item.optLong("createdAt", now)
                // Expiration date is strictly fixed at creation; viewing/editing NEVER resets it
                val expiresAt = item.optLong("expiresAt", createdAt + EXPIRATION_DURATION_MS)

                val msLeft = expiresAt - now

                if (msLeft <= 0) {
                    // Expired: Permanently delete file from private app storage
                    val file = File(filePath)
                    if (file.exists()) {
                        file.delete()
                    }
                    deletedCount++
                } else {
                    // Still valid within 30-day window: Retain in database
                    survivingArray.put(item)
                }
            }

            // Save updated metadata back to private file
            metadataFile.writeText(survivingArray.toString())

            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
