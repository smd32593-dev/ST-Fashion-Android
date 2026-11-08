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
fun MannequinGeneratorScreen(isBengali: Boolean) {
    var mannequinStyle by remember { mutableStateOf("ghost") } // ghost, matte_black, wooden, white, chrome
    var mannequinPose by remember { mutableStateOf("front") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = if (isBengali) "এআই ম্যানিকুইন জেনারেটর" else "AI Mannequin Generator",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = if (isBengali) "পোশাকের নিখুঁত ফিটিং, বাস্তবসম্মত ড্রেপ ও প্রিমিয়াম স্টুডিও ম্যানিকুইন" else "Realistic clothing fitting, 3D body contouring and natural fabric drape",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSecondary
            )
        }

        // Preview Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
            modifier = Modifier
                .fillMaxWidth()
                .height(340.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1610030469983-98e550d6193c?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Mannequin Fit Preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                Surface(
                    color = Color.Black.copy(alpha = 0.8f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                ) {
                    Text(
                        text = "Fitted: Jamdani Sari | Style: $mannequinStyle",
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Mannequin Type Selection
        Text(
            text = if (isBengali) "ম্যানিকুইনের ধরন নির্বাচন" else "Select Mannequin Aesthetic",
            style = MaterialTheme.typography.titleSmall,
            color = Color.White
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(
                Triple("ghost", "Invisible Ghost Mannequin (Hollow 3D)", "ইনভিজিবল ঘোস্ট ম্যানিকুইন (ফাঁপা ৩ডি)"),
                Triple("matte_black", "Matte Black Fashion Runway", "ম্যাট ব্ল্যাক ফ্যাশন রানওয়ে"),
                Triple("wooden", "Natural Handcrafted Wooden", "ন্যাচারাল কাঠের আর্টিকুলেটেড"),
                Triple("white", "Minimalist Studio White", "মিনিমালিস্ট স্টুডিও হোয়াইট"),
                Triple("chrome", "High-Gloss Studio Chrome", "হাই-গ্লস স্টুডিও ক্রোম")
            ).forEach { (id, en, bn) ->
                val selected = mannequinStyle == id
                FilterChip(
                    selected = selected,
                    onClick = { mannequinStyle = id },
                    label = { Text(if (isBengali) bn else en) },
                    modifier = Modifier.fillMaxWidth(),
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
            onClick = { /* Generate fit */ },
            colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.AutoFixHigh, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isBengali) "ম্যানিকুইন ফিটিং জেনারেট করুন" else "Generate 3D Mannequin Fit")
        }
    }
}
