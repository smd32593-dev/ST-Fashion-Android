package com.stfashion.creativehouse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.stfashion.creativehouse.ui.theme.*

@Composable
fun ProductPhotoMakerScreen(isBengali: Boolean) {
    var isProcessing by remember { mutableStateOf(false) }
    var showOriginal by remember { mutableStateOf(false) }
    var selectedView by remember { mutableStateOf("front") } // front, back, closeup
    var mannequinMode by remember { mutableStateOf("mannequin_free") } // ghost, mannequin, mannequin_free
    var voiceStatusText by remember { mutableStateOf<String?>(null) }
    var wrinkleRemovalActive by remember { mutableStateOf(true) }
    var preserveEmbroidery by remember { mutableStateOf(true) }

    // Sample flagship garment (Dubai Abaya with gold zari embroidery)
    val originalImageUrl = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?auto=format&fit=crop&w=800&q=80"
    val reconstructedImageUrl = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?auto=format&fit=crop&w=1000&q=90"

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Module Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = if (isBengali) "এআই প্রোডাক্ট ফটো মেকার" else "AI Product Photo Maker",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
                Text(
                    text = if (isBengali) "স্মার্ট ক্লথিং রিকনস্ট্রাকশন ও ভাঁজ দূরীকরণ" else "Smart Garment Reconstruction & Wrinkle Elimination",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedSecondary
                )
            }
            Badge(containerColor = RoyalPurpleDark) {
                Text("FLAGSHIP", color = Color.White)
            }
        }

        // Main Studio Preview Card with Before/After
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = if (showOriginal) originalImageUrl else reconstructedImageUrl,
                    contentDescription = "Garment Studio View",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top Floating Badge: Mode & View Angle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.75f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = if (showOriginal) "ORIGINAL CAPTURE" else "AI RECONSTRUCTED (WRINKLES REMOVED)",
                            color = if (showOriginal) GoldAccent else StatusActiveGreen,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }

                    Surface(
                        color = RoyalPurplePrimary.copy(alpha = 0.9f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "30-DAY RETENTION",
                            color = Color.White,
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                // Hold/Toggle Comparison Button
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 14.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { showOriginal = !showOriginal },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black.copy(alpha = 0.8f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Icon(Icons.Default.Compare, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (showOriginal) (if (isBengali) "এআই রিকনস্ট্রাকশন দেখুন" else "Show AI Result")
                            else (if (isBengali) "আসল ছবি দেখুন" else "Hold For Original"),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        // View Angle Controls (Front, Back, Close-up)
        Text(
            text = if (isBengali) "ভিউ অ্যাঙ্গেল নির্বাচন" else "View Angle Angle Selection",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Triple("front", "Front View", "সামনের ভিউ"),
                Triple("back", "Back View", "পেছনের ভিউ"),
                Triple("closeup", "Close-up (Lace/Zari)", "ক্লোজ-আপ (লেইস/জরির কাজ)")
            ).forEach { (id, en, bn) ->
                val selected = selectedView == id
                FilterChip(
                    selected = selected,
                    onClick = { selectedView = id },
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

        // Presentation Mode (Mannequin vs Mannequin-Free)
        Text(
            text = if (isBengali) "উপস্থাপন মোড" else "Presentation Mode",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                Triple("mannequin_free", "Mannequin-Free (Flat/Floating)", "ম্যানিকুইন ছাড়া (ফ্ল্যাট/ফ্লোটিং)"),
                Triple("ghost", "Invisible Ghost Mannequin", "ইনভিজিবল ঘোস্ট ম্যানিকুইন"),
                Triple("matte_black", "Matte Black Studio", "ম্যাট ব্ল্যাক স্টুডিও")
            ).forEach { (id, en, bn) ->
                val selected = mannequinMode == id
                FilterChip(
                    selected = selected,
                    onClick = { mannequinMode = id },
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

        // Bengali Voice Command Quick Chips
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.Mic, contentDescription = null, tint = RoyalPurplePrimary)
                    Text(
                        text = if (isBengali) "বাংলা ভয়েস কমান্ড দিয়ে কারেকশন করুন" else "Bengali Voice Smart Corrections",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val voiceCommands = listOf(
                    "এই কালারটা আরেকটু সুন্দর করো",
                    "শার্পনেস বাড়াও",
                    "এমব্রয়ডারি আরও স্পষ্ট করো",
                    "ব্যাকগ্রাউন্ড টা প্রিমিয়াম করো",
                    "তোমার মতে ছবিটা আরও সুন্দর করে দাও"
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(voiceCommands.size) { index ->
                        val cmd = voiceCommands[index]
                        SuggestionChip(
                            onClick = {
                                voiceStatusText = "কমান্ড প্রয়োগ হয়েছে: \"$cmd\""
                            },
                            label = { Text(cmd, style = MaterialTheme.typography.bodySmall) },
                            colors = SuggestionChipDefaults.suggestionChipColors(
                                containerColor = DarkCardSurface,
                                labelColor = LightGold
                            )
                        )
                    }
                }

                voiceStatusText?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it, color = StatusActiveGreen, style = MaterialTheme.typography.bodySmall)
                }
            }
        }

        // Action Buttons: Save to MediaStore Gallery with 30-day Countdown
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { /* MediaStore export */ },
                colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.Download, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isBengali) "গ্যালারিতে সেভ করুন" else "Save To Gallery")
            }

            OutlinedButton(
                onClick = { /* Camera or photo picker */ },
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(Icons.Default.AddPhotoAlternate, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isBengali) "নতুন ছবি আপলোড" else "Upload Garment", color = Color.White)
            }
        }
    }
}
