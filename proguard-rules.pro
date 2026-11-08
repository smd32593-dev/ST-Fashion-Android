# ProGuard rules for ST Fashion Creative House
# Optimized for Samsung Galaxy A30 (3GB RAM) and low-memory devices

# Keep data models
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep Compose runtime
-keep class androidx.compose.** { *; }

# Keep Coil image loader
-keep class coil.** { *; }

# Keep WorkManager worker
-keep class * extends androidx.work.Worker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}
-keep class * extends androidx.work.CoroutineWorker {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}

# Keep Coroutines
-keep class kotlinx.coroutines.** { *; }

# Keep OkHttp
-dontwarn okhttp3.**
-dontwarn okio.**
-keep class okhttp3.** { *; }
