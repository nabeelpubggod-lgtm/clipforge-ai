package com.clipforge.ai

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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


/* ---------------- SCREEN STATE ---------------- */

private enum class Screen {
    Dashboard,
    Create,
    Analysis,
    Projects,
    Settings
}


/* ---------------- APP ---------------- */

@Composable
fun ClipForgeApp() {

    var screen by remember {
        mutableStateOf(Screen.Dashboard)
    }

    var videoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            videoUri = uri
        }
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF09051F)
        ) {

            when (screen) {

                Screen.Dashboard -> Dashboard(
                    create = {
                        screen = Screen.Create
                    },
                    analysis = {
                        screen = Screen.Analysis
                    },
                    projects = {
                        screen = Screen.Projects
                    },
                    settings = {
                        screen = Screen.Settings
                    }
                )

                Screen.Create -> CreateScreen(
                    videoUri = videoUri,
                    pickVideo = {
                        picker.launch(arrayOf("video/*"))
                    },
                    continueAnalysis = {
                        if (videoUri != null) {
                            screen = Screen.Analysis
                        }
                    },
                    back = {
                        screen = Screen.Dashboard
                    }
                )

                Screen.Analysis -> AnalysisScreen(
                    videoUri = videoUri,
                    back = {
                        screen = Screen.Dashboard
                    }
                )

                Screen.Projects -> SimpleScreen(
                    title = "My Projects",
                    icon = "📁",
                    message = "Your saved projects and generated clips will appear here.",
                    back = {
                        screen = Screen.Dashboard
                    }
                )

                Screen.Settings -> SimpleScreen(
                    title = "Settings",
                    icon = "⚙️",
                    message = "AI preferences, video quality and account settings will appear here.",
                    back = {
                        screen = Screen.Dashboard
                    }
                )
            }
        }
    }
}


/* ---------------- BACKGROUND ---------------- */

@Composable
fun AppBackground(
    content: @Composable () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF08041D),
                        Color(0xFF12083B),
                        Color(0xFF09051F)
                    )
                )
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF7138FF).copy(alpha = 0.35f),
                            Color.Transparent
                        )
                    )
                )
        )

        content()
    }
}


/* ---------------- TOP BAR ---------------- */

@Composable
fun Header(
    title: String,
    back: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 18.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (back != null) {

            Text(
                text = "‹",
                color = Color.White,
                fontSize = 42.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clickable {
                        back()
                    }
            )
        }

        Column {

            Text(
                text = "CLIPFORGE AI",
                color = Color(0xFFAA8CFF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Text(
                text = title,
                color = Color.White,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


/* ---------------- DASHBOARD ---------------- */

@Composable
fun Dashboard(
    create: () -> Unit,
    analysis: () -> Unit,
    projects: () -> Unit,
    settings: () -> Unit
) {

    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            Text(
                text = "CLIPFORGE",
                color = Color(0xFFAE8CFF),
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 3.sp
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Turn long videos\ninto viral clips.",
                color = Color.White,
                fontSize = 35.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 40.sp
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "AI-powered video clipping built for creators.",
                color = Color(0xFFB7B0CB),
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            GlassCard {

                Text(
                    text = "🎬",
                    fontSize = 40.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Create your next clip",
                    color = Color.White,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(6.dp)
                )

                Text(
                    text = "Upload a long-form video and let AI find the strongest moments.",
                    color = Color(0xFFAAA4BF),
                    fontSize = 14.sp,
                    lineHeight = 21.sp
                )

                Spacer(
                    modifier = Modifier.height(18.dp)
                )

                PurpleButton(
                    text = "Create New Clip  →",
                    onClick = create
                )
            }

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "CREATOR TOOLS",
                color = Color(0xFF8379A8),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            ToolButton(
                icon = "🤖",
                title = "AI Clip Analysis",
                subtitle = "Find the strongest moments",
                onClick = analysis
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            ToolButton(
                icon = "📁",
                title = "My Projects",
                subtitle = "Saved clips and projects",
                onClick = projects
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            ToolButton(
                icon = "⚙️",
                title = "Settings",
                subtitle = "Preferences and AI settings",
                onClick = settings
            )

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            GlassCard {

                Text(
                    text = "✨ AI WORKFLOW",
                    color = Color(0xFFB99AFF),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Analyze  •  Clip  •  Edit  •  Export",
                    color = Color.White,
                    fontSize = 15.sp
                )
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )
        }
    }
}


/* ---------------- CREATE SCREEN ---------------- */

@Composable
fun CreateScreen(
    videoUri: Uri?,
    pickVideo: () -> Unit,
    continueAnalysis: () -> Unit,
    back: () -> Unit
) {

    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Header(
                title = "Create New Clip",
                back = back
            )

            Column(
                modifier = Modifier.padding(horizontal = 20.dp)
            ) {

                Text(
                    text = "Select your video",
                    color = Color.White,
                    fontSize = 31.sp,
                    fontWeight = FontWeight.ExtraBold
                )

                Spacer(
                    modifier = Modifier.height(8.dp)
                )

                Text(
                    text = "Choose a long-form video and prepare it for AI analysis.",
                    color = Color(0xFFAAA4BF),
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                GlassCard {

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "📁",
                            fontSize = 52.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Select Video",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "MP4 • MOV • AVI • MKV",
                            color = Color(0xFF8F88AA),
                            fontSize = 12.sp
                        )

                        Spacer(
                            modifier = Modifier.height(15.dp)
                        )

                        OutlinedButton(
                            onClick = pickVideo,
                            shape = RoundedCornerShape(15.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.White
                            )
                        ) {

                            Text(
                                text = if (videoUri == null) {
                                    "Choose Video"
                                } else {
                                    "Change Video"
                                }
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                if (videoUri != null) {

                    GlassCard {

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFF20D99A)),
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = "✓",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(
                                modifier = Modifier.width(12.dp)
                            )

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
                                    text = "Ready for analysis",
                                    color = Color(0xFF75E3BE),
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(
                            modifier = Modifier.height(12.dp)
                        )

                        Text(
                            text = videoUri.toString(),
                            color = Color(0xFF9690AC),
                            fontSize = 10.sp,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    Spacer(
                        modifier = Modifier.height(18.dp)
                    )

                    PurpleButton(
                        text = "✨  Continue to AI Analysis  →",
                        onClick = continueAnalysis
                    )

                } else {

                    PurpleButton(
                        text = "Select a Video First",
                        enabled = false,
                        onClick = {}
                    )
                }

                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    FeatureBox(
                        icon = "🤖",
                        title = "AI",
                        modifier = Modifier.weight(1f)
                    )

                    FeatureBox(
                        icon = "✂️",
                        title = "Auto",
                        modifier = Modifier.weight(1f)
                    )

                    FeatureBox(
                        icon = "HD",
                        title = "Quality",
                        modifier = Modifier.weight(1f)
                    )

                    FeatureBox(
                        icon = "⚡",
                        title = "Fast",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(
                    modifier = Modifier.height(30.dp)
                )
            }
        }
    }
}


/* ---------------- ANALYSIS SCREEN ---------------- */

@Composable
fun AnalysisScreen(
    videoUri: Uri?,
    back: () -> Unit
) {

    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Header(
                title = "AI Clip Analysis",
                back = back
            )

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Ready to discover\nviral moments?",
                    color = Color.White,
                    fontSize = 33.sp,
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 39.sp
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Text(
                    text = "Your selected video is ready for the ClipForge AI analysis pipeline.",
                    color = Color(0xFFAAA4BF),
                    fontSize = 15.sp,
                    lineHeight = 22.sp
                )

                Spacer(
                    modifier = Modifier.height(22.dp)
                )

                GlassCard {

                    Text(
                        text = "🤖",
                        fontSize = 45.sp
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = "AI Analysis Pi
/* ---------------- SIMPLE SCREEN ---------------- */

@Composable
fun SimpleScreen(
    title: String,
    icon: String,
    message: String,
    back: () -> Unit
) {
    AppBackground {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            Header(
                title = title,
                back = back
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(
                    modifier = Modifier.height(60.dp)
                )

                GlassCard {

                    Text(
                        text = icon,
                        fontSize = 55.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(15.dp)
                    )

                    Text(
                        text = title,
                        color = Color.White,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = message,
                        color = Color(0xFFAAA4BF),
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        textAlign = TextAlign.Center
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    PurpleButton(
                        text = "← Back to Dashboard",
                        onClick = back
                    )
                }
            }
        }
    }
}


/* ---------------- GLASS CARD ---------------- */

@Composable
fun GlassCard(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(
                Color(0xFF17102F).copy(alpha = 0.92f)
            )
            .border(
                width = 1.dp,
                color = Color(0xFF6045A0).copy(alpha = 0.45f),
                shape = RoundedCornerShape(24.dp)
            )
            .padding(18.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            content()
        }
    }
}


/* ---------------- PURPLE BUTTON ---------------- */

@Composable
fun PurpleButton(
    text: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF7138FF),
            contentColor = Color.White,
            disabledContainerColor = Color(0xFF332A4D),
            disabledContentColor = Color(0xFF817A91)
        )
    ) {
        Text(
            text = text,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


/* ---------------- TOOL BUTTON ---------------- */

@Composable
fun ToolButton(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color(0xFF15102C))
            .border(
                width = 1.dp,
                color = Color(0xFF493878).copy(alpha = 0.55f),
                shape = RoundedCornerShape(18.dp)
            )
            .clickable {
                onClick()
            }
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(15.dp))
                .background(Color(0xFF271A50)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = icon,
                fontSize = 23.sp
            )
        }

        Spacer(
            modifier = Modifier.width(14.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {

            Text(
                text = title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(3.dp)
            )

            Text(
                text = subtitle,
                color = Color(0xFF928BAA),
                fontSize = 12.sp
            )
        }

        Text(
            text = "›",
            color = Color(0xFFAA8CFF),
            fontSize = 28.sp
        )
    }
}


/* ---------------- FEATURE BOX ---------------- */

@Composable
fun FeatureBox(
    icon: String,
    title: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF15102C))
            .border(
                width = 1.dp,
                color = Color(0xFF493878).copy(alpha = 0.45f),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = icon,
            color = Color.White,
            fontSize = 20.sp
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = title,
            color = Color(0xFFBDB5D0),
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}


/* ---------------- PIPELINE ROW ---------------- */

@Composable
fun PipelineRow(
    number: String,
    title: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF2A1B55)),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = number,
                color = Color(0xFFB99AFF),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Text(
            text = title,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
