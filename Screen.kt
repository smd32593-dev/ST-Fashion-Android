package com.stfashion.creativehouse.navigation

sealed class Screen(val route: String, val titleEn: String, val titleBn: String) {
    object Home : Screen("home", "Home", "হোম")
    object ProductPhotoMaker : Screen("product_photo", "AI Product Photo", "প্রোডাক্ট ফটো")
    object VideoMaker : Screen("video_maker", "AI Video Maker", "ভিডিও মেকার")
    object MannequinGenerator : Screen("mannequin", "AI Mannequin", "ম্যানিকুইন")
    object PhotoEditor : Screen("photo_editor", "AI Photo Editor", "ফটো এডিটর")
    object PosterMaker : Screen("poster_maker", "AI Poster & Ad", "পোস্টার ও অ্যাড")
    object StorageManager : Screen("storage", "30-Day Auto Delete", "৩০ দিনের অটো ডিলিট")
}
