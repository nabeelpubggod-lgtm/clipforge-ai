package com.clipforge.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ClipForgeApp()
        }
    }
}

@Composable
fun ClipForgeApp() {
    var screen by remember { mutableStateOf("welcome") }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            when (screen) {
                "welcome" -> WelcomeScreen(
                    onGetStarted = { screen = "dashboard" }
                )

                "dashboard" -> DashboardScreen(
                    onCreateClip = { screen = "create" },
                    onAnalysis = { screen = "analysis" },
                    onProjects = { screen = "projects" },
                    onSettings = { screen = "settings" }
                )

                "create" -> FeatureScreen(
                    title = "Create New Clip",
                    description = "Select a long-form video and turn the best moments into short-form clips.",
                    onBack = { screen = "dashboard" }
                )

                "analysis" -> FeatureScreen(
                    title = "AI Clip Analysis",
                    description = "ClipForge AI will analyze your video and identify the strongest moments for short-form content.",
                    onBack = { screen = "dashboard" }
                )

                "projects" -> FeatureScreen(
                    title = "My Projects",
                    description = "Your saved ClipForge AI projects and generated clips will appear here.",
                    onBack = { screen = "dashboard" }
                )

                "settings" -> FeatureScreen(
                    title = "Settings",
                    description = "App preferences, AI settings and account options will be available here.",
                    onBack = { screen = "dashboard" }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ClipForge AI",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "AI-powered professional video clipping and editing",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onGetStarted,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Get Started",
                fontSize = 17.sp
            )
        }
    }
}

@Composable
fun DashboardScreen(
    onCreateClip: () -> Unit,
    onAnalysis: () -> Unit,
    onProjects: () -> Unit,
    onSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Text(
            text = "ClipForge AI",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Professional AI video clipping",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        DashboardCard(
            title = "Create New Clip",
            description = "Turn long videos into powerful short-form clips.",
            buttonText = "Create",
            onClick = onCreateClip
        )

        Spacer(modifier = Modifier.height(14.dp))

        DashboardCard(
            title = "AI Clip Analysis",
            description = "Find the strongest moments automatically.",
            buttonText = "Analyze",
            onClick = onAnalysis
        )

        Spacer(modifier = Modifier.height(14.dp))

        DashboardCard(
            title = "My Projects",
            description = "View and manage your saved projects.",
            buttonText = "Open",
            onClick = onProjects
        )

        Spacer(modifier = Modifier.height(14.dp))

        DashboardCard(
            title = "Settings",
            description = "Configure ClipForge AI.",
            buttonText = "Settings",
            onClick = onSettings
        )
    }
}

@Composable
fun DashboardCard(
    title: String,
    description: String,
    buttonText: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = description,
                    fontSize = 14.sp
                )
            }

            Button(onClick = onClick) {
                Text(text = buttonText)
            }
        }
    }
}

@Composable
fun FeatureScreen(
    title: String,
    description: String,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = description,
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(onClick = onBack) {
            Text(text = "Back to Dashboard")
        }
    }
}
