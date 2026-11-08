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
fun PosterMakerScreen(isBengali: Boolean) {
    var posterHeadline by remember {
        mutableStateOf(if (isBengali) "ঈদ উৎসবের রাজকীয় সংগ্রহ ২০২৬" else "Royal Eid Collection 2026")
    }
    var discountBadge by remember {
        mutableStateOf(if (isBengali) "ফ্ল্যাট ৩০% ছাড়" else "FLAT 30% OFF")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column {
            Text(
                text = if (isBengali) "এআই পোস্টার ও অ্যাড মেকার" else "AI Fashion Poster & Ad Maker",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = if (isBengali) "সোশ্যাল মিডিয়া প্রচারণার জন্য প্রিমিয়াম ফ্যাশন পোস্টার ডিজাইন করুন" else "Create high-converting social media ads with luxury typography and price tags",
                style = MaterialTheme.typography.bodySmall,
                color = TextMutedSecondary
            )
        }

        // Poster Visual Card
        Card(
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
            modifier = Modifier
                .fillMaxWidth()
                .height(380.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                AsyncImage(
                    model = "https://images.unsplash.com/photo-1584917865442-de89df76afd3?auto=format&fit=crop&w=800&q=80",
                    contentDescription = "Poster Preview",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Top Luxury Header Banner Overlay
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.6f))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "ST FASHION CREATIVE HOUSE",
                        color = GoldAccent,
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = posterHeadline,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                // Bottom Discount & CTA
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(Color.Black.copy(alpha = 0.7f))
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Badge(containerColor = GoldAccent) {
                        Text(discountBadge, color = Color.Black, modifier = Modifier.padding(4.dp))
                    }
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (isBengali) "অর্ডার করুন" else "Shop Now")
                    }
                }
            }
        }

        // Headline Input
        OutlinedTextField(
            value = posterHeadline,
            onValueChange = { posterHeadline = it },
            label = { Text(if (isBengali) "পোস্টার শিরোনাম (বাংলা/ইংরেজি)" else "Poster Headline") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        OutlinedTextField(
            value = discountBadge,
            onValueChange = { discountBadge = it },
            label = { Text(if (isBengali) "ডিসকাউন্ট বা অফার টেক্সট" else "Discount / Offer Tag") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        Button(
            onClick = { /* Export Poster */ },
            colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Download, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(if (isBengali) "হাই-রেজোলিউশন পোস্টার ডাউনলোড" else "Export High-Res Ad Poster")
        }
    }
}
