# ST Fashion Creative House
## Native Android Project & Samsung Galaxy A30 Deployment Guide

### Overview
**ST Fashion Creative House** is a lightweight, high-performance native Android application engineered in **Kotlin** with **Jetpack Compose (Material 3)**.

- **Minimum SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34 (Android 14)
- **Primary Tested Environment**: Android 11 (API 30) on **Samsung Galaxy A30 (SM-A305F / SM-A305G / SM-A305FN)**

---

### Samsung Galaxy A30 Hardware & Low-Memory Optimizations
The Samsung Galaxy A30 features the **Exynos 7904 Octa-core chipset** with **3GB or 4GB LPDDR4x RAM** and a **720 x 1560 HD+ Super AMOLED display**.

To ensure 100% smooth 60fps operation without crashes or OutOfMemory (OOM) errors:
1. **Dynamic Subsampling (`SamsungA30Optimizer.kt`)**: Decodes large garment images using `inJustDecodeBounds` and calculates `inSampleSize` matching the 720p A30 viewport.
2. **RGB_565 Color Config on Low RAM**: Uses 16-bit RGB_565 on low-memory detection, cutting Dalvik heap usage in half compared to ARGB_8888.
3. **`android:largeHeap="true"`**: Configured in `AndroidManifest.xml` to expand the maximum heap size from 192MB to 256MB/512MB for multi-layer garment reconstruction.
4. **Hardware Acceleration**: Enabled GPU rasterization with Mali-G71 optimization.
5. **Scoped Storage Compliance**: Saves exports to `MediaStore.Images.Media.EXTERNAL_CONTENT_URI` (`Pictures/STFashionCreativeHouse`) without needing deprecated legacy storage permissions.

---

### 30-Day Automatic File Deletion Architecture
To protect proprietary fashion designs and prevent low-storage warnings on budget devices:
1. **Android WorkManager (`FileExpirationWorker.kt`)**:
   - Executes a daily background job via `PeriodicWorkRequest`.
   - Checks `createdAt` and `expiresAt` timestamps.
   - **Crucial Rule**: Opening, viewing, downloading, or editing a file **never** resets its original expiration timestamp. Every new generation receives its own independent 30-day lifecycle.
   - Deletes files silently and strictly upon reaching the 30-day expiration without intrusive notifications.
2. **`BootReceiver.kt`**:
   - Re-registers the WorkManager job when the Samsung device restarts (`BOOT_COMPLETED`).
3. **Cloud Scheduled Cleanup**:
   - Backend endpoint (`POST /api/cleanup-expired`) automatically purges cloud-stored media and database records independently of app launches.

---

### How to Build & Install on Samsung Galaxy A30

#### Step 1: Enable Developer Options on Samsung Galaxy A30
1. Open **Settings** on your Samsung Galaxy A30.
2. Scroll down and tap **About phone** > **Software information**.
3. Tap **Build number** 7 times until you see the toast: *"Developer mode has been enabled"*.
4. Return to **Settings** > tap the newly unlocked **Developer options**.
5. Enable **USB debugging**.

#### Step 2: Open Project in Android Studio
1. Launch **Android Studio Iguana | Jellyfish | Koala** (or newer).
2. Click **Open** and select the `/android` folder.
3. Allow Gradle to sync dependencies (`Jetpack Compose BOM 2024.02.00`, `WorkManager 2.9.0`, `Coil 2.6.0`).

#### Step 3: Build the APK
- **Debug APK**:
  Run in terminal:
  ```bash
  ./gradlew assembleDebug
  ```
  The installable APK will be generated at:
  `app/build/outputs/apk/debug/app-debug.apk`

- **Release APK**:
  ```bash
  ./gradlew assembleRelease
  ```

#### Step 4: Install Directly via USB (ADB)
Connect your Samsung Galaxy A30 with a USB-C cable:
```bash
adb devices
# Verify your Samsung Galaxy A30 is listed
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

#### Step 5: Samsung One UI Battery Whitelist (Important for WorkManager)
Samsung One UI aggressively puts background apps to sleep. To guarantee the 30-day deletion worker runs on schedule:
1. Go to **Settings** > **Apps** > **ST Fashion Creative House**.
2. Tap **Battery**.
3. Select **Unrestricted** (or add to *"Never sleeping apps"*).

---

### API Configuration
- **Gemini API Key**: Automatically passed through `GEMINI_API_KEY` on the backend server.
- The app supports offline garment image pre-processing with graceful online sync for Gemini 3.8 Flash model generation.
