package com.clipforge.ai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),

                horizontalAlignment = Alignment.CenterHorizontally,

                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "ClipForge AI",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Text(
                    text = "AI-powered professional video clipping and editing"
                )

                Spacer(
                    modifier = Modifier.height(28.dp)
                )

                Button(
                    onClick = {}
                ) {

                    Text(
                        text = "Get Started"
                    )
                }
            }
        }
    }
}
