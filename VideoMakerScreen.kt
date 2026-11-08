package com.stfashion.creativehouse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stfashion.creativehouse.ui.theme.*

@Composable
fun VideoMakerScreen(isBengali: Boolean) {
    var selectedMotion by remember { mutableStateOf("zoom_in") } // zoom_in, pan_down, orbit_360
    var selectedFormat by remember { mutableStateOf("9:16") } // 9:16 Reels/TikTok, 1:1 Feed, 16:9 Landscape
    var isGenerating by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = if (isBengali) "এআই ভিডিও মেকার" else "AI Fashion Video Maker",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = if (isBengali) "পোশাকের স্টিল ফটো থেকে প্রফেশনাল সোশ্যাল মিডিয়া রিল ও ভিডিও" else "Generate dynamic fashion videos from garment photos with smooth camera motion",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSecondary
            )
        }

        // Video Player Preview Box
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Fashion Showcase Video",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Overlay Controls
                Surface(
                    color = Color.Black.copy(alpha = 0.65f),
                    shape = RoundedCornerShape(28.dp),
                    modifier = Modifier.size(56.dp)
                ) {
                    IconButton(onClick = { /* play/pause */ }) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.White)
                    }
                }

                Surface(
                    color = Color.Black.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Format: $selectedFormat | Motion: $selectedMotion",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Camera Motion Selection
        Text(
            text = if (isBengali) "ক্যামেরা মোশন স্টাইল" else "Camera Movement Style",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                Triple("zoom_in", "Slow Zoom In", "ধীর জুম-ইন"),
                Triple("pan_down", "Macro Pan Down", "ম্যাক্রো প্যান-ডাউন"),
                Triple("orbit_360", "360 Turntable Orbit", "৩৬০ টার্নটেবল অরবিট")
            ).forEach { (id, en, bn) ->
                val selected = selectedMotion == id
                FilterChip(
                    selected = selected,
                    onClick = { selectedMotion = id },
                    label = { Text(if (isBengali) bn else en) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoyalPurplePrimary,
                        selectedLabelColor = Color.White,
                        containerColor = DarkElevatedSurface,
                        labelColor = TextMutedSecondary
                    )
                )
            }
        }

        // Social Media Video Format
        Text(
            text = if (isBengali) "ভিডিও ফরম্যাট ও অ্যাসপেক্ট রেশিও" else "Social Video Format & Aspect Ratio",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                Triple("9:16", "9:16 (Instagram Reels / TikTok)", "৯:১৬ (রিলস/টিকটক)"),
                Triple("1:1", "1:1 (Square Feed)", "১:১ (স্কয়ার পোস্ট)"),
                Triple("16:9", "16:9 (Landscape Web)", "১৬:৯ (ওয়েবসাইট)")
            ).forEach { (id, en, bn) ->
                val selected = selectedFormat == id
                FilterChip(
                    selected = selected,
                    onClick = { selectedFormat = id },
                    label = { Text(if (isBengali) bn else en) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = RoyalPurplePrimary,
                        selectedLabelColor = Color.White,
                        containerColor = DarkElevatedSurface,
                        labelColor = TextMutedSecondary
                    )
                )
            }
        }

        Button(
            onClick = { isGenerating = true },
            colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.MovieCreation, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isBengali) "ভিডিও তৈরি ও এক্সপোর্ট করুন" else "Render & Export Fashion Video")
        }
    }
}
