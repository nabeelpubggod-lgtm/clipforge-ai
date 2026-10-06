package com.clipforge.ai

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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

private enum class Screen {
    Dashboard,
    CreateClip,
    Analysis,
    Projects,
    Settings
}

@Composable
fun ClipForgeApp() {

    var currentScreen by remember {
        mutableStateOf(Screen.Dashboard)
    }

    var selectedVideoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val videoPicker =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument()
        ) { uri ->

            if (uri != null) {
                selectedVideoUri = uri
            }
        }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {

            when (currentScreen) {

                Screen.Dashboard -> {
                    DashboardScreen(
                        onCreateClip = {
                            currentScreen = Screen.CreateClip
                        },
                        onAnalysis = {
                            currentScreen = Screen.Analysis
                        },
                        onProjects = {
                            currentScreen = Screen.Projects
                        },
                        onSettings = {
                            currentScreen = Screen.Settings
                        }
                    )
                }

                Screen.CreateClip -> {
                    CreateClipScreen(
                        selectedVideoUri = selectedVideoUri,
                        onPickVideo = {
                            videoPicker.launch(arrayOf("video/*"))
                        },
                        onContinue = {
                            if (selectedVideoUri != null) {
                                currentScreen = Screen.Analysis
                            }
                        },
                        onBack = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }

                Screen.Analysis -> {
                    AnalysisScreen(
                        selectedVideoUri = selectedVideoUri,
                        onBack = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }

                Screen.Projects -> {
                    ProjectsScreen(
                        onBack = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }

                Screen.Settings -> {
                    SettingsScreen(
                        onBack = {
                            currentScreen = Screen.Dashboard
                        }
                    )
                }
            }
        }
    }
}

/* ------------------------------------------------ */
/* BACKGROUND */
/* ------------------------------------------------ */

@Composable
fun ClipForgeBackground(
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF09051F),
                        Color(0xFF10083A),
                        Color(0xFF08051D)
                    )
                )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF632BFF).copy(alpha = 0.30f),
                            Color.Transparent
                        ),
                        radius = 700f
                    )
                )
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 500.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFFB21FFF).copy(alpha = 0.18f),
                            Color.Transparent
                        ),
                        radius = 600f
                    )
                )
        )

        content()
    }
}

/* ------------------------------------------------ */
/* TOP BAR */
/* ------------------------------------------------ */

@Composable
fun TopBar(
    title: String,
    onBack: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (onBack != null) {

            Text(
                text = "‹",
                color = Color.White,
                fontSize = 40.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
            )
        }

        Column {

            Text(
                text = "CLIPFORGE AI",
                color = Color(0xFFB99AFF),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/* ------------------------------------------------ */
/* DASHBOARD */
/* ------------------------------------------------ */

@Composable
fun DashboardScreen(
    onCreateClip: () -> Unit,
    onAnalysis: () -> Unit,
    onProjects: () -> Unit,
    onSettings: () -> Unit
) {

    ClipForgeBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(20.dp)
        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "CLIPFORGE",
                color = Color(0xFFB58CFF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Turn long videos\ninto viral clips.",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 42.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "AI-powered video clipping designed for creators.",
                color = Color(0xFFB9B3D0),
                fontSize = 16.sp,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            /* MAIN CREATE CARD */

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF17113B).copy(alpha = 0.92f)
                ),
                border = BorderStroke(
                    1.dp,
                    Color(0xFF714DFF).copy(alpha = 0.55f)
                )
            ) {

                Column(
                    modifier = Modifier.padding(24.dp)
                ) {

                    Text(
                        text = "🎬",
                        fontSize = 38.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "Create your next clip",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = "Upload a long-form video and let ClipForge find the strongest moments.",
                        color = Color(0xFFBDB7D5),
                        fontSize = 14.sp,
                        lineHeight = 21.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    GradientButton(
                        text = "Create New Clip  →",
                        onClick = onCreateClip
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "POWERFUL CREATOR TOOLS",
                color = Color(0xFF8E82B9),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.5.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            ToolCard(
                icon = "🤖",
                title = "AI Clip Analysis",
                subtitle = "Find the strongest moments",
                onClick = onAnalysis
            )

            Spacer(modifier = Modifier.height(10.dp))

            ToolCard(
                icon = "📁",
                title = "My Projects",
                subtitle = "Your saved clips and projects",
                onClick = onProjects
            )

            Spacer(modifier = Modifier.height(10.dp))

            ToolCard(
                icon = "⚙️",
                title = "Settings",
                subtitle = "Preferences and AI settings",
                onClick = onSettings
            )

            Spacer(modifier = Modifier.height(28.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF15102F)
                )
            ) {

                Row(
                    modifier = Modifier.padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "✨",
                        fontSize = 28.sp
                    )

                    Spacer(modifier = Modifier.width(14.dp))

                    Column {

                        Text(
                            text = "AI-powered workflow",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )

                        Text(
                            text = "Analyze • Clip • Edit • Export",
                            color = Color(0xFF9F96BE),
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

/* ------------------------------------------------ */
/* CREATE CLIP */
/* ------------------------------------------------ */

@Composable
fun CreateClipScreen(
    selectedVideoUri: Uri?,
    onPickVideo: () -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit
) {

    ClipForgeBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .navigationBarsPadding()
        ) {

            TopBar(
                title = "Create New Clip",
                onBack = onBack
            )

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Upload your long-form video",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 36.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "ClipForge AI will analyze it and identify the best moments for short-form content.",
                    color = Color(0xFFB8B1CE),
                    fontSize = 15.sp,
                    lineHeight = 23.sp
                )

                Spacer(modifier = Modifier.height(25.dp))

                /* UPLOAD AREA */

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(210.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF12102D).copy(alpha = 0.85f)
                    ),
                    border = BorderStroke(
                        1.dp,
                        Color(0xFF7354FF).copy(alpha = 0.8f)
                    )
                ) {

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "📁",
                            fontSize = 48.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Select Video",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        Text(
                            text = "MP4 • MOV • AVI • MKV",
                            color = Color(0xFF9690B4),
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedButton(
                            onClick = onPickVideo,
                            shape = RoundedCornerShape(16.dp),
                            border = BorderStroke(
                                1.dp,
                                Color(0xFF8D69FF)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {

                            Text(
                                text = if (selectedVideoUri == null)
                                    "Choose Video"
                                else
                                    "Change Video"
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                /* SELECTED VIDEO */

                if (selectedVideoUri != null) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF17143A)
                        ),
                        border = BorderStroke(
                            1.dp,
                            Color(0xFF4E3C9E)
                        )
                    ) {

                        Column(
                            modifier = Modifier.padding(18.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "✓",
                                    color = Color.White,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(RoundedCornerShape(14.dp))
                                        .background(Color(0xFF26D89A))
                                        .padding(9.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {

                                    Text(
                                        text = "Video Selected",
                                        color = Color.White,
                                        fontSize = 17.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    Text(
                                        text = "Ready for AI analysis",
                                        color = Color(0xFF8DDFC5),
                                        fontSize = 12.sp
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = selectedVideoUri.toString(),
                                color = Color(0xFFAAA3C3),
                                fontSize = 11.sp,
                                maxLines = 3,
                            )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedButton(
            onClick = onBack
        ) {
            Text("Back to Dashboard")
        }
    }
}
