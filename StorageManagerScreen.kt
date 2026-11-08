package com.stfashion.creativehouse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.stfashion.creativehouse.ui.theme.*
import com.stfashion.creativehouse.workers.FileExpirationWorker

@Composable
fun StorageManagerScreen(isBengali: Boolean) {
    val context = LocalContext.current
    var isCleaning by remember { mutableStateOf(false) }
    var cleanupSuccessMsg by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = DarkElevatedSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(Icons.Default.Schedule, contentDescription = null, tint = RoyalPurplePrimary)
                        Text(
                            text = if (isBengali) "৩০ দিনের অটো ফাইল ডিলিট সিস্টেম" else "30-Day Automatic File Deletion",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (isBengali)
                            "গোপনীয়তা রক্ষা ও মেমোরি সুরক্ষার জন্য আপলোড ও জেনারেট করা সকল ফাইল ঠিক ৩০ দিন পর অ্যাপের প্রাইভেট স্টোরেজ ও ক্লাউড থেকে স্থায়ীভাবে মুছে যাবে। ফাইল ওপেন বা এডিট করলেও মূল মেয়াদকাল রিসেট হবে না। ব্যাকগ্রাউন্ডে শান্তভাবে ও নিখুঁতভাবে ৩০ দিন পূর্ণ হলে ফাইল মুছে যায়।"
                        else
                            "To safeguard fashion IP and maintain optimal device storage, all app-managed uploaded and generated files are automatically purged strictly after 30 days. Opening, viewing, or editing never resets the expiration date. Background cleanup executes silently without intrusive notifications.",
                        style = MaterialTheme.typography.bodySmall,
                        color = TextMutedSecondary
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Button(
                        onClick = {
                            isCleaning = true
                            FileExpirationWorker.triggerImmediateCleanup(context)
                            cleanupSuccessMsg = if (isBengali) "৩০ দিনের ক্লিনআপ সফলভাবে সম্পন্ন হয়েছে" else "30-Day expiration worker executed"
                            isCleaning = false
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = RoyalPurplePrimary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.CleaningServices, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(if (isBengali) "এখনই এক্সপায়ার্ড ফাইল মুছুন" else "Run Expiration Cleanup Now")
                    }

                    cleanupSuccessMsg?.let {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(it, color = StatusActiveGreen, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }

        item {
            Text(
                text = if (isBengali) "সংরক্ষিত ফাইল ও অবশিষ্ট দিন" else "Managed Files & Remaining Days",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }

        val sampleItems = listOf(
            Pair("Black Dubai Abaya - Gold Zari", 26),
            Pair("Royal Heritage Jamdani Sari", 15),
            Pair("Classic Black Burqa with Cutwork", 8)
        )

        items(sampleItems.size) { index ->
            val (title, daysLeft) = sampleItems[index]
            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = DarkCardSurface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(title, color = Color.White, style = MaterialTheme.typography.titleSmall)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = if (isBengali) "মেয়াদ বাকি: $daysLeft দিন" else "Expires in: $daysLeft days",
                            color = TextMutedSecondary,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Badge(containerColor = RoyalPurplePrimary.copy(alpha = 0.2f)) {
                        Text(
                            text = "$daysLeft Days Left",
                            color = RoyalPurpleLight,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }
        }
    }
}
