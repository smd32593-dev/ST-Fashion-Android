package com.stfashion.creativehouse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.stfashion.creativehouse.navigation.Screen
import com.stfashion.creativehouse.ui.theme.*

@Composable
fun HomeScreen(
    isBengali: Boolean,
    onNavigate: (Screen) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        // Hero Studio Banner
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    RoyalPurpleDark.copy(alpha = 0.5f),
                                    DarkCardSurface
                                )
                            )
                        )
                        .padding(20.dp)
                ) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Badge(containerColor = GoldAccent) {
                                Text("A30 OPTIMIZED", color = Color.Black)
                            }
                            Badge(containerColor = RoyalPurplePrimary) {
                                Text("30-DAY AUTO PURGE", color = Color.White)
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = if (isBengali) "স্মার্ট ক্লথিং রিকনস্ট্রাকশন" else "Smart Clothing Reconstruction",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = if (isBengali)
                                "কুঁচকানো পোশাক সোজা করুন, আসল এমব্রয়ডারি ও লেইস অক্ষত রাখুন এবং প্রফেশনাল স্টুডিও শট তৈরি করুন।"
                            else
                                "Straighten wrinkles, preserve authentic embroidery & lace, and produce high-end studio fashion photography.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = TextMutedSecondary
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = { onNavigate(Screen.ProductPhotoMaker) },
                            colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AutoFixHigh, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = if (isBengali) "ফটো তৈরি শুরু করুন" else "Start Photo Reconstruction",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }

        // Section Title: 5 Main Studio Modules
        item {
            Text(
                text = if (isBengali) "স্টুডিওর মূল মডিউলসমূহ" else "Main Studio Modules",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        // Module 1: AI Product Photo Maker (The Flagship)
        item {
            ModuleCard(
                title = if (isBengali) "১. এআই প্রোডাক্ট ফটো মেকার" else "1. AI Product Photo Maker",
                subtitle = if (isBengali)
                    "ভাঁজ দূরীকরণ, আসল নকশা ও ফেব্রিক টেক্সচার সংরক্ষণ, ম্যানিকুইন ও ফ্রি মোড।"
                else
                    "Wrinkle removal, original embroidery/lace preservation, multi-angle views.",
                badge = if (isBengali) "প্রধান মডিউল" else "FLAGSHIP",
                icon = Icons.Default.PhotoCamera,
                gradient = listOf(RoyalPurplePrimary, RoyalPurpleDark),
                onClick = { onNavigate(Screen.ProductPhotoMaker) }
            )
        }

        // Module 2: AI Video Maker
        item {
            ModuleCard(
                title = if (isBengali) "২. এআই ভিডিও মেকার" else "2. AI Video Maker",
                subtitle = if (isBengali)
                    "পোশাকের ছবি থেকে রিয়েল ফ্যাশন শোকেস ভিডিও, জুম ও প্যান মোশন, সোশ্যাল মিডিয়া ফরম্যাট।"
                else
                    "Smooth camera movement, macro pan & zoom, 9:16 Reels & TikTok showcase.",
                badge = "HD EXPORT",
                icon = Icons.Default.Videocam,
                gradient = listOf(Color(0xFFEC4899), Color(0xFF9D174D)),
                onClick = { onNavigate(Screen.VideoMaker) }
            )
        }

        // Module 3: AI Mannequin Generator
        item {
            ModuleCard(
                title = if (isBengali) "৩. এআই ম্যানিকুইন জেনারেটর" else "3. AI Mannequin Generator",
                subtitle = if (isBengali)
                    "ইনভিজিবল ঘোস্ট ম্যানিকুইন, ম্যাট ব্ল্যাক ও রিয়েলিস্টিক উডেন ড্রেপিং ফিটিং।"
                else
                    "Ghost mannequin, matte black & studio wooden styles with natural drape.",
                badge = "REALISTIC DRAPE",
                icon = Icons.Default.AccessibilityNew,
                gradient = listOf(Color(0xFF3B82F6), Color(0xFF1E40AF)),
                onClick = { onNavigate(Screen.MannequinGenerator) }
            )
        }

        // Module 4: AI Photo Editor
        item {
            ModuleCard(
                title = if (isBengali) "৪. এআই ফটো এডিটর" else "4. AI Photo Editor",
                subtitle = if (isBengali)
                    "লাক্সারি স্টুডিও ব্যাকগ্রাউন্ড, লাইটিং সংশোধন, শার্পনেস বুস্ট ও স্প্লিট কম্প্যারিজন।"
                else
                    "Background studio swap, lighting correction, before/after slider.",
                badge = "INSTANT EDIT",
                icon = Icons.Default.Tune,
                gradient = listOf(Color(0xFF10B981), Color(0xFF065F46)),
                onClick = { onNavigate(Screen.PhotoEditor) }
            )
        }

        // Module 5: AI Poster & Ad Maker
        item {
            ModuleCard(
                title = if (isBengali) "৫. এআই পোস্টার ও অ্যাড মেকার" else "5. AI Poster and Ad Maker",
                subtitle = if (isBengali)
                    "ঈদ ও ফেস্টিভ্যাল বিজ্ঞাপন, বাংলা ও ইংরেজি টাইপোগ্রাফি, ডিসকাউন্ট ব্যাজ ও পোস্টার।"
                else
                    "Promotional social ads, Eid festival presets, price badges & export.",
                badge = "MARKETING READY",
                icon = Icons.Default.Campaign,
                gradient = listOf(Color(0xFFF59E0B), Color(0xFFB45309)),
                onClick = { onNavigate(Screen.PosterMaker) }
            )
        }
    }
}

@Composable
fun ModuleCard(
    title: String,
    subtitle: String,
    badge: String,
    icon: ImageVector,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Brush.linearGradient(gradient)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.White
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextMutedSecondary
                )
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.Gray
            )
        }
    }
}
