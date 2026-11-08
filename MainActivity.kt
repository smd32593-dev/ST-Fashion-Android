package com.stfashion.creativehouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.stfashion.creativehouse.navigation.Screen
import com.stfashion.creativehouse.ui.screens.*
import com.stfashion.creativehouse.ui.theme.DarkStudioBackground
import com.stfashion.creativehouse.ui.theme.RoyalPurplePrimary
import com.stfashion.creativehouse.ui.theme.STFashionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isBengali by remember { mutableStateOf(false) }

            STFashionTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(
                                        text = if (isBengali) "এসটি ফ্যাশন ক্রিয়েটিভ হাউস" else "ST Fashion Creative House",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
                                    )
                                    Text(
                                        text = if (isBengali) "এআই ফ্যাশন স্টুডিও • ৩০ দিনের অটো ডিলিট সক্রিয়" else "AI Fashion Studio • 30-Day Auto Delete Active",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                }
                            },
                            actions = {
                                // Bengali / English toggle button
                                TextButton(
                                    onClick = { isBengali = !isBengali },
                                    colors = ButtonDefaults.textButtonColors(contentColor = RoyalPurplePrimary)
                                ) {
                                    Text(
                                        text = if (isBengali) "ENG" else "বাংলা",
                                        style = MaterialTheme.typography.labelLarge
                                    )
                                }
                                IconButton(onClick = { navController.navigate(Screen.StorageManager.route) }) {
                                    BadgedBox(
                                        badge = {
                                            Badge { Text("30d") }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.DeleteSweep,
                                            contentDescription = "30-Day Storage",
                                            tint = Color.White
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = DarkStudioBackground
                            )
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = DarkStudioBackground,
                            tonalElevation = 8.dp
                        ) {
                            val items = listOf(
                                Triple(Screen.Home, Icons.Default.Home, Icons.Outlined.Home),
                                Triple(Screen.ProductPhotoMaker, Icons.Default.PhotoCamera, Icons.Outlined.PhotoCamera),
                                Triple(Screen.MannequinGenerator, Icons.Default.AccessibilityNew, Icons.Outlined.AccessibilityNew),
                                Triple(Screen.VideoMaker, Icons.Default.Videocam, Icons.Outlined.Videocam),
                                Triple(Screen.PosterMaker, Icons.Default.AutoAwesome, Icons.Outlined.AutoAwesome),
                            )

                            items.forEach { (screen, selectedIcon, unselectedIcon) ->
                                val selected = currentRoute == screen.route
                                NavigationBarItem(
                                    selected = selected,
                                    onClick = {
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route) {
                                                popUpTo(Screen.Home.route) { saveState = true }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = if (selected) selectedIcon else unselectedIcon,
                                            contentDescription = screen.titleEn,
                                            tint = if (selected) RoyalPurplePrimary else Color.Gray
                                        )
                                    },
                                    label = {
                                        Text(
                                            text = if (isBengali) screen.titleBn else screen.titleEn,
                                            color = if (selected) RoyalPurplePrimary else Color.Gray,
                                            style = MaterialTheme.typography.labelSmall
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(DarkStudioBackground)
                            .padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                isBengali = isBengali,
                                onNavigate = { screen -> navController.navigate(screen.route) }
                            )
                        }
                        composable(Screen.ProductPhotoMaker.route) {
                            ProductPhotoMakerScreen(isBengali = isBengali)
                        }
                        composable(Screen.VideoMaker.route) {
                            VideoMakerScreen(isBengali = isBengali)
                        }
                        composable(Screen.MannequinGenerator.route) {
                            MannequinGeneratorScreen(isBengali = isBengali)
                        }
                        composable(Screen.PhotoEditor.route) {
                            PhotoEditorScreen(isBengali = isBengali)
                        }
                        composable(Screen.PosterMaker.route) {
                            PosterMakerScreen(isBengali = isBengali)
                        }
                        composable(Screen.StorageManager.route) {
                            StorageManagerScreen(isBengali = isBengali)
                        }
                    }
                }
            }
        }
    }
}
