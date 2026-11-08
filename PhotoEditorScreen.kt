package com.stfashion.creativehouse.ui.screens

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
fun PhotoEditorScreen(isBengali: Boolean) {
    var selectedStudioBg by remember { mutableStateOf("marble") }
    var brightnessSlider by remember { mutableFloatStateOf(10f) }
    var sharpnessSlider by remember { mutableFloatStateOf(25f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = if (isBengali) "এআই ফটো এডিটর" else "AI Photo Editor & Studio Enhancer",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = if (isBengali) "ব্যাকগ্রাউন্ড রিমুভ ও রিপ্লেসমেন্ট, লাইটিং কারেকশন ও ফ্যাব্রিক শার্পনেস" else "Background studio replacement, lighting balance and fabric micro-detail enhancement",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSecondary
            )
        }

        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
        ) {
            AsyncImage(
                model = "https://images.unsplash.com/photo-1594938298603-c8148c4dae35?auto=format&fit=crop&w=800&q=80",
                contentDescription = "Photo Editor Preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        // Studio Backgrounds
        Text(
            text = if (isBengali) "লাক্সারি স্টুডিও ব্যাকগ্রাউন্ড" else "Studio Background Presets",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                Triple("marble", "Luxury Marble Pedestal", "মার্বেল পেডেস্টাল"),
                Triple("parisian", "Parisian Minimalist", "প্যারিসিয়ান মিনিমালিস্ট"),
                Triple("spotlight", "Editorial Spotlight", "এডিটোরিয়াল স্পটলাইট")
            ).forEach { (id, en, bn) ->
                val selected = selectedStudioBg == id
                FilterChip(
                    selected = selected,
                    onClick = { selectedStudioBg = id },
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

        // Sliders
        Text(
            text = if (isBengali) "শার্পনেস ও মাইক্রো ডিটেইল" else "Sharpness & Micro-Stitch Clarity: ${sharpnessSlider.toInt()}%",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
        Slider(
            value = sharpnessSlider,
            onValueChange = { sharpnessSlider = it },
            valueRange = 0f..100f,
            colors = SliderDefaults.colors(
                thumbColor = RoyalPurplePrimary,
                activeTrackColor = RoyalPurplePrimary
            )
        )

        Button(
            onClick = { /* Apply edits */ },
            colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Done, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isBengali) "পরিবর্তনগুলো সেভ করুন" else "Apply & Save To Gallery")
        }
    }
}
